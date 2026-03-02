package com.lidev.mycountriesapp.data.repository

import com.lidev.mycountriesapp.data.datasource.local.SettingsDataStore
import com.lidev.mycountriesapp.domain.repository.SettingsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

internal class SettingsRepositoryImpl(
    private val settingsDataStore: SettingsDataStore,
) : SettingsRepository {
    override val themeFlow: Flow<String> = settingsDataStore.themeFlow
    override val paletteFlow: Flow<String> = settingsDataStore.paletteFlow
    override val dynamicColorFlow: Flow<Boolean> = settingsDataStore.dynamicColorFlow
    override val languageFlow: Flow<String> = settingsDataStore.languageFlow

    override suspend fun setTheme(theme: String) =
        withContext(Dispatchers.IO) {
            settingsDataStore.setTheme(theme)
        }

    override suspend fun setPalette(palette: String) =
        withContext(Dispatchers.IO) {
            settingsDataStore.setPalette(palette)
        }

    override suspend fun setDynamicColor(enabled: Boolean) =
        withContext(Dispatchers.IO) {
            settingsDataStore.setDynamicColor(enabled)
        }

    override suspend fun setLanguage(language: String) =
        withContext(Dispatchers.IO) {
            settingsDataStore.setLanguage(language)
        }
}
