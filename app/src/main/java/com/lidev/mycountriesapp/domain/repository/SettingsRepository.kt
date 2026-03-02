package com.lidev.mycountriesapp.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    val themeFlow: Flow<String>
    val dynamicColorFlow: Flow<Boolean>
    val languageFlow: Flow<String>

    suspend fun setTheme(theme: String)

    suspend fun setDynamicColor(enabled: Boolean)

    suspend fun setLanguage(language: String)
}
