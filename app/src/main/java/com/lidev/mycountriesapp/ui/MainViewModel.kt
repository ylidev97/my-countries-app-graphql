package com.lidev.mycountriesapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lidev.mycountriesapp.domain.usecases.GetDynamicColorUseCase
import com.lidev.mycountriesapp.domain.usecases.GetLanguageUseCase
import com.lidev.mycountriesapp.domain.usecases.GetThemeUseCase
import com.lidev.mycountriesapp.ui.screens.settings.model.SettingsState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class MainViewModel(
    getThemeUseCase: GetThemeUseCase,
    getDynamicColorUseCase: GetDynamicColorUseCase,
    getLanguageUseCase: GetLanguageUseCase,
) : ViewModel() {
    val state: StateFlow<SettingsState> =
        combine(
            getThemeUseCase(),
            getDynamicColorUseCase(),
            getLanguageUseCase(),
        ) { theme, dynamicColor, language ->
            SettingsState(theme, dynamicColor, language)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = SettingsState(),
        )
}
