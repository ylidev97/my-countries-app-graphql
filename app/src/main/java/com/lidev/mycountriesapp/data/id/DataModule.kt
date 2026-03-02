package com.lidev.mycountriesapp.data.id

import com.apollographql.apollo.ApolloClient
import com.lidev.mycountriesapp.data.datasource.ApolloCountryClient
import com.lidev.mycountriesapp.data.datasource.SettingsDataStore
import com.lidev.mycountriesapp.data.repository.CountryRepositoryImpl
import com.lidev.mycountriesapp.data.repository.SettingsRepositoryImpl
import com.lidev.mycountriesapp.domain.repository.CountryRepository
import com.lidev.mycountriesapp.domain.repository.SettingsRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataModule =
    module {
        single {
            ApolloClient
                .Builder()
                .serverUrl("https://countries.trevorblades.com/graphql")
                .build()
        }

        factory<ApolloCountryClient> {
            ApolloCountryClient(get())
        }

        factory<CountryRepository> {
            CountryRepositoryImpl(get())
        }

        singleOf(::SettingsDataStore)

        singleOf(::SettingsRepositoryImpl) bind SettingsRepository::class
    }
