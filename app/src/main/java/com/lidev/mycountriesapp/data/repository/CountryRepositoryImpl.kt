package com.lidev.mycountriesapp.data.repository

import com.lidev.mycountriesapp.data.database.dao.CountryDao
import com.lidev.mycountriesapp.data.datasource.remote.ApolloCountryClient
import com.lidev.mycountriesapp.data.mappers.local.toDetailDomain
import com.lidev.mycountriesapp.data.mappers.local.toDomain
import com.lidev.mycountriesapp.data.mappers.local.toEntity
import com.lidev.mycountriesapp.domain.model.Country
import com.lidev.mycountriesapp.domain.model.CountryDetail
import com.lidev.mycountriesapp.domain.repository.CountryRepository
import com.lidev.mycountriesapp.domain.repository.SettingsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

internal class CountryRepositoryImpl(
    private val apolloCountryClient: ApolloCountryClient,
    private val countryDao: CountryDao,
    private val settingsRepository: SettingsRepository,
) : CountryRepository {
    override suspend fun getCountries(): Result<List<Country>> =
        withContext(Dispatchers.IO) {
            val isOffline = settingsRepository.offlineModeFlow.first()

            if (isOffline) {
                val localCountries = countryDao.getCountries().first().map { it.toDomain() }
                return@withContext Result.success(localCountries)
            }

            val remoteResult = apolloCountryClient.getCountries()

            remoteResult.onSuccess { remoteCountries ->
                // Basic data update, don't delete everything to keep details if possible
                // Better approach: insert with REPLACE
                countryDao.insertCountries(remoteCountries.map { it.toEntity() })
            }

            val localCountries = countryDao.getCountries().first().map { it.toDomain() }
            if (localCountries.isNotEmpty()) {
                Result.success(localCountries)
            } else {
                remoteResult
            }
        }

    override suspend fun getCountryByCode(code: String): Result<CountryDetail?> =
        withContext(Dispatchers.IO) {
            val isOffline = settingsRepository.offlineModeFlow.first()

            if (isOffline) {
                val localCountry = countryDao.getCountryByCode(code)?.toDetailDomain()
                return@withContext Result.success(localCountry)
            }

            val remoteResult = apolloCountryClient.getCountryByCode(code)

            remoteResult.onSuccess { remoteCountry ->
                remoteCountry?.let {
                    countryDao.insertCountry(it.toEntity())
                }
            }

            val localCountry = countryDao.getCountryByCode(code)?.toDetailDomain()
            Result.success(localCountry)
        }
}
