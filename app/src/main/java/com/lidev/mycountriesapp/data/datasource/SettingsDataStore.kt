package com.lidev.mycountriesapp.data.datasource

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingsDataStore(
    private val context: Context,
) {
    companion object {
        val THEME_KEY = stringPreferencesKey("theme")
        val DYNAMIC_COLOR_KEY = booleanPreferencesKey("dynamic_color")
        val LANGUAGE_KEY = stringPreferencesKey("language")
    }

    val themeFlow: Flow<String> =
        context.dataStore.data.map { preferences ->
            preferences[THEME_KEY] ?: "system"
        }

    val dynamicColorFlow: Flow<Boolean> =
        context.dataStore.data.map { preferences ->
            preferences[DYNAMIC_COLOR_KEY] ?: true
        }

    val languageFlow: Flow<String> =
        context.dataStore.data.map { preferences ->
            preferences[LANGUAGE_KEY] ?: "system"
        }

    suspend fun setTheme(theme: String) {
        context.dataStore.edit { preferences ->
            preferences[THEME_KEY] = theme
        }
    }

    suspend fun setDynamicColor(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[DYNAMIC_COLOR_KEY] = enabled
        }
    }

    suspend fun setLanguage(language: String) {
        context.dataStore.edit { preferences ->
            preferences[LANGUAGE_KEY] = language
        }
    }
}
