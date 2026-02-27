package com.lidev.mycountriesapp

import com.lidev.mycountriesapp.domain.usecases.GetCountriesUseCase
import com.lidev.mycountriesapp.domain.usecases.GetCountryByCodeUseCase
import com.lidev.mycountriesapp.ui.screens.countries.CountriesScreenViewModel
import com.lidev.mycountriesapp.util.NetworkMonitor
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule =
    module {
        single { NetworkMonitor(androidContext()) }
        factoryOf(::GetCountriesUseCase)
        factoryOf(::GetCountryByCodeUseCase)
        viewModelOf(::CountriesScreenViewModel)
    }
