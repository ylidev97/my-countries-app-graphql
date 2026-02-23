package com.lidev.mycountriesapp.data.repository

import com.lidev.mycountriesapp.data.datasource.ApolloCountryClient
import com.lidev.mycountriesapp.domain.model.Country
import com.lidev.mycountriesapp.domain.model.CountryDetail
import com.lidev.mycountriesapp.domain.repository.CountryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class CountryRepositoryImpl(
    private val apolloCountryClient: ApolloCountryClient
) : CountryRepository {

    override suspend fun getCountries(): Result<List<Country>> =
        withContext(Dispatchers.IO) {
            apolloCountryClient.getCountries()
        }


    override suspend fun getCountryByCode(code: String): Result<CountryDetail?> =
        withContext(Dispatchers.IO) {
            apolloCountryClient.getCountryByCode(code)
        }

}
