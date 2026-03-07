package com.lidev.mycountriesapp.data.repository

import com.lidev.mycountriesapp.data.datasource.local.SettingsDataStore
import com.lidev.mycountriesapp.domain.model.AppLanguage
import com.lidev.mycountriesapp.domain.model.AppPalette
import com.lidev.mycountriesapp.domain.model.AppTheme
import com.lidev.mycountriesapp.domain.repository.SettingsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

internal class SettingsRepositoryImpl(
    private val settingsDataStore: SettingsDataStore,
) : SettingsRepository {
    override val themeFlow: Flow<AppTheme> =
        settingsDataStore.themeFlow.map { AppTheme.fromKey(it) }
    override val paletteFlow: Flow<AppPalette> =
        settingsDataStore.paletteFlow.map { AppPalette.fromKey(it) }
    override val dynamicColorFlow: Flow<Boolean> = settingsDataStore.dynamicColorFlow
    override val languageFlow: Flow<AppLanguage> =
        settingsDataStore.languageFlow.map { AppLanguage.fromTag(it) }
    override val offlineModeFlow: Flow<Boolean> = settingsDataStore.offlineModeFlow

    override suspend fun setTheme(theme: AppTheme) =
        withContext(Dispatchers.IO) {
            settingsDataStore.setTheme(theme.key)
        }

    override suspend fun setPalette(palette: AppPalette) =
        withContext(Dispatchers.IO) {
            settingsDataStore.setPalette(palette.key)
        }

    override suspend fun setDynamicColor(enabled: Boolean) =
        withContext(Dispatchers.IO) {
            settingsDataStore.setDynamicColor(enabled)
        }

    override suspend fun setLanguage(language: AppLanguage) =
        withContext(Dispatchers.IO) {
            settingsDataStore.setLanguage(language.tag)
        }

    override suspend fun setOfflineMode(enabled: Boolean) =
        withContext(Dispatchers.IO) {
            settingsDataStore.setOfflineMode(enabled)
        }
}
