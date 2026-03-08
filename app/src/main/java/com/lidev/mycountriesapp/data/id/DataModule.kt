package com.lidev.mycountriesapp.data.id

import androidx.room.Room
import com.apollographql.apollo.ApolloClient
import com.lidev.mycountriesapp.data.database.CountryDatabase
import com.lidev.mycountriesapp.data.datasource.local.SettingsDataStore
import com.lidev.mycountriesapp.data.datasource.remote.ApolloCountryClient
import com.lidev.mycountriesapp.data.repository.CountryRepositoryImpl
import com.lidev.mycountriesapp.data.repository.SettingsRepositoryImpl
import com.lidev.mycountriesapp.domain.repository.CountryRepository
import com.lidev.mycountriesapp.domain.repository.SettingsRepository
import org.koin.android.ext.koin.androidContext
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

        single {
            Room
                .databaseBuilder(
                    androidContext(),
                    CountryDatabase::class.java,
                    "countries.db",
                ).build()
        }

        single { get<CountryDatabase>().countryDao }

        factory<ApolloCountryClient> {
            ApolloCountryClient(get())
        }

        factory<CountryRepository> {
            CountryRepositoryImpl(get(), get(), get())
        }

        singleOf(::SettingsDataStore)

        singleOf(::SettingsRepositoryImpl) bind SettingsRepository::class
    }
