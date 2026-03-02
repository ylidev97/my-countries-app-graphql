package com.lidev.mycountriesapp.data.repository

import com.lidev.mycountriesapp.data.datasource.SettingsDataStore
import com.lidev.mycountriesapp.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow

class SettingsRepositoryImpl(
    private val settingsDataStore: SettingsDataStore,
) : SettingsRepository {
    override val themeFlow: Flow<String> = settingsDataStore.themeFlow
    override val dynamicColorFlow: Flow<Boolean> = settingsDataStore.dynamicColorFlow
    override val languageFlow: Flow<String> = settingsDataStore.languageFlow

    override suspend fun setTheme(theme: String) = settingsDataStore.setTheme(theme)

    override suspend fun setDynamicColor(enabled: Boolean) = settingsDataStore.setDynamicColor(enabled)

    override suspend fun setLanguage(language: String) = settingsDataStore.setLanguage(language)
}
