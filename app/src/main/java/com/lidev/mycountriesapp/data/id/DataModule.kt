package com.lidev.mycountriesapp.data.id

import com.apollographql.apollo.ApolloClient
import com.lidev.mycountriesapp.data.datasource.ApolloCountryClient
import com.lidev.mycountriesapp.data.repository.CountryRepositoryImpl
import com.lidev.mycountriesapp.domain.repository.CountryRepository
import org.koin.dsl.module

val dataModule = module {
    single {
        ApolloClient.Builder()
            .serverUrl("https://countries.trevorblades.com/graphql")
            .build()
    }

    factory<ApolloCountryClient> {
        ApolloCountryClient(get())
    }

    factory<CountryRepository> {
        CountryRepositoryImpl(get())
    }
}