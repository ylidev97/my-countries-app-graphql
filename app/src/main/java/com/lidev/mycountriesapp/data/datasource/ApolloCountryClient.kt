package com.lidev.mycountriesapp.data.datasource

import com.apollographql.apollo.ApolloClient
import com.lidev.mycountriesapp.countriestrevorblades.GetCountriesQuery
import com.lidev.mycountriesapp.countriestrevorblades.GetCountryByCodeQuery
import com.lidev.mycountriesapp.data.mappers.toDomain
import com.lidev.mycountriesapp.data.util.safeApiCall
import com.lidev.mycountriesapp.domain.model.Country
import com.lidev.mycountriesapp.domain.model.CountryDetail

class ApolloCountryClient(
    private val apolloClient: ApolloClient,
) {
    suspend fun getCountries(): Result<List<Country>> {
        return safeApiCall {
            apolloClient
                .query(GetCountriesQuery())
                .execute()
                .dataAssertNoErrors
                .countries
                .map { it.toDomain() }
        }
    }

    suspend fun getCountryByCode(code: String): Result<CountryDetail?> {
        return safeApiCall {
            apolloClient
                .query(GetCountryByCodeQuery(code))
                .execute()
                .dataAssertNoErrors
                .country?.toDomain()
        }
    }
}
