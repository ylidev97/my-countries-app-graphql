package com.lidev.mycountriesapp.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lidev.mycountriesapp.domain.model.AppLanguage
import com.lidev.mycountriesapp.domain.model.AppPalette
import com.lidev.mycountriesapp.domain.model.AppTheme
import com.lidev.mycountriesapp.domain.usecases.GetDynamicColorUseCase
import com.lidev.mycountriesapp.domain.usecases.GetLanguageUseCase
import com.lidev.mycountriesapp.domain.usecases.GetPaletteUseCase
import com.lidev.mycountriesapp.domain.usecases.GetThemeUseCase
import com.lidev.mycountriesapp.domain.usecases.SetDynamicColorUseCase
import com.lidev.mycountriesapp.domain.usecases.SetLanguageUseCase
import com.lidev.mycountriesapp.domain.usecases.SetPaletteUseCase
import com.lidev.mycountriesapp.domain.usecases.SetThemeUseCase
import com.lidev.mycountriesapp.ui.screens.settings.model.SettingsState
import com.lidev.mycountriesapp.ui.widget.WidgetSyncManager
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
    private val widgetSyncManager: WidgetSyncManager,
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

    fun setTheme(theme: AppTheme) {
        viewModelScope.launch {
            setThemeUseCase(theme)
            widgetSyncManager.updateWidgets()
        }
    }

    fun setPalette(palette: AppPalette) {
        viewModelScope.launch {
            setPaletteUseCase(palette)
            widgetSyncManager.updateWidgets()
        }
    }

    fun setDynamicColor(enabled: Boolean) {
        viewModelScope.launch {
            setDynamicColorUseCase(enabled)
            widgetSyncManager.updateWidgets()
        }
    }

    fun setLanguage(language: AppLanguage) {
        viewModelScope.launch {
            setLanguageUseCase(language)
            widgetSyncManager.updateWidgets()
        }
    }
}
