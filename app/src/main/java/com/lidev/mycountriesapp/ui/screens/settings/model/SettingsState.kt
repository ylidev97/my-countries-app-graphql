package com.lidev.mycountriesapp.ui.screens.settings.model

import com.lidev.mycountriesapp.domain.model.AppLanguage
import com.lidev.mycountriesapp.domain.model.AppPalette
import com.lidev.mycountriesapp.domain.model.AppTheme

data class SettingsState(
    val theme: AppTheme = AppTheme.System,
    val palette: AppPalette = AppPalette.Default,
    val dynamicColor: Boolean = true,
    val language: AppLanguage = AppLanguage.System,
    val notificationsEnabled: Boolean = false,
)
