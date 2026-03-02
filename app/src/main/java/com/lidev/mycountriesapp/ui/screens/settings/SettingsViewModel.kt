package com.lidev.mycountriesapp.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lidev.mycountriesapp.domain.usecases.GetDynamicColorUseCase
import com.lidev.mycountriesapp.domain.usecases.GetLanguageUseCase
import com.lidev.mycountriesapp.domain.usecases.GetPaletteUseCase
import com.lidev.mycountriesapp.domain.usecases.GetThemeUseCase
import com.lidev.mycountriesapp.domain.usecases.SetDynamicColorUseCase
import com.lidev.mycountriesapp.domain.usecases.SetLanguageUseCase
import com.lidev.mycountriesapp.domain.usecases.SetPaletteUseCase
import com.lidev.mycountriesapp.domain.usecases.SetThemeUseCase
import com.lidev.mycountriesapp.ui.screens.settings.model.SettingsState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(
    getThemeUseCase: GetThemeUseCase,
    getPaletteUseCase: GetPaletteUseCase,
    getDynamicColorUseCase: GetDynamicColorUseCase,
    getLanguageUseCase: GetLanguageUseCase,
    private val setThemeUseCase: SetThemeUseCase,
    private val setPaletteUseCase: SetPaletteUseCase,
    private val setDynamicColorUseCase: SetDynamicColorUseCase,
    private val setLanguageUseCase: SetLanguageUseCase,
) : ViewModel() {
    val state: StateFlow<SettingsState> =
        combine(
            getThemeUseCase(),
            getPaletteUseCase(),
            getDynamicColorUseCase(),
            getLanguageUseCase(),
        ) { theme, palette, dynamicColor, language ->
            SettingsState(theme, palette, dynamicColor, language)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = SettingsState(),
        )

    fun setTheme(theme: String) {
        viewModelScope.launch {
            setThemeUseCase(theme)
        }
    }

    fun setPalette(palette: String) {
        viewModelScope.launch {
            setPaletteUseCase(palette)
        }
    }

    fun setDynamicColor(enabled: Boolean) {
        viewModelScope.launch {
            setDynamicColorUseCase(enabled)
        }
    }

    fun setLanguage(language: String) {
        viewModelScope.launch {
            setLanguageUseCase(language)
        }
    }
}
