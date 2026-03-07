package com.lidev.mycountriesapp.domain.repository

import com.lidev.mycountriesapp.domain.model.AppLanguage
import com.lidev.mycountriesapp.domain.model.AppPalette
import com.lidev.mycountriesapp.domain.model.AppTheme
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    val themeFlow: Flow<AppTheme>
    val paletteFlow: Flow<AppPalette>
    val dynamicColorFlow: Flow<Boolean>
    val languageFlow: Flow<AppLanguage>
    val offlineModeFlow: Flow<Boolean>

    suspend fun setTheme(theme: AppTheme)

    suspend fun setPalette(palette: AppPalette)

    suspend fun setDynamicColor(enabled: Boolean)

    suspend fun setLanguage(language: AppLanguage)

    suspend fun setOfflineMode(enabled: Boolean)
}
