package com.lidev.mycountriesapp.ui

import com.lidev.mycountriesapp.domain.manager.NotificationScheduler
import com.lidev.mycountriesapp.domain.usecases.GetAppInfoUseCase
import com.lidev.mycountriesapp.domain.usecases.GetCountriesUseCase
import com.lidev.mycountriesapp.domain.usecases.GetCountryByCodeUseCase
import com.lidev.mycountriesapp.domain.usecases.GetDynamicColorUseCase
import com.lidev.mycountriesapp.domain.usecases.GetLanguageUseCase
import com.lidev.mycountriesapp.domain.usecases.GetNotificationsEnabledUseCase
import com.lidev.mycountriesapp.domain.usecases.GetPaletteUseCase
import com.lidev.mycountriesapp.domain.usecases.GetThemeUseCase
import com.lidev.mycountriesapp.domain.usecases.SetDynamicColorUseCase
import com.lidev.mycountriesapp.domain.usecases.SetLanguageUseCase
import com.lidev.mycountriesapp.domain.usecases.SetNotificationsEnabledUseCase
import com.lidev.mycountriesapp.domain.usecases.SetPaletteUseCase
import com.lidev.mycountriesapp.domain.usecases.SetThemeUseCase
import com.lidev.mycountriesapp.ui.manager.WorkManagerNotificationScheduler
import com.lidev.mycountriesapp.ui.screens.countries.CountriesScreenViewModel
import com.lidev.mycountriesapp.ui.screens.settings.SettingsViewModel
import com.lidev.mycountriesapp.ui.usecase.GetAppInfoUseCaseImpl
import com.lidev.mycountriesapp.ui.util.NetworkMonitor
import com.lidev.mycountriesapp.ui.widget.WidgetSyncManager
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule =
    module {
        single { NetworkMonitor(androidContext()) }
        single { WidgetSyncManager(androidContext()) }
        singleOf(::GetAppInfoUseCaseImpl) bind GetAppInfoUseCase::class
        singleOf(::WorkManagerNotificationScheduler) bind NotificationScheduler::class

        factoryOf(::GetCountriesUseCase)
        factoryOf(::GetCountryByCodeUseCase)

        factoryOf(::GetThemeUseCase)
        factoryOf(::GetPaletteUseCase)
        factoryOf(::GetDynamicColorUseCase)
        factoryOf(::GetLanguageUseCase)
        factoryOf(::GetNotificationsEnabledUseCase)
        factoryOf(::SetThemeUseCase)
        factoryOf(::SetPaletteUseCase)
        factoryOf(::SetDynamicColorUseCase)
        factoryOf(::SetLanguageUseCase)
        factoryOf(::SetNotificationsEnabledUseCase)

        viewModelOf(::CountriesScreenViewModel)
        viewModelOf(::SettingsViewModel)
        viewModelOf(::MainViewModel)
    }
