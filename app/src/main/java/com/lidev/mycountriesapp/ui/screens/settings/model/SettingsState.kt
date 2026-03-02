package com.lidev.mycountriesapp.ui.screens.settings.model

data class SettingsState(
    val theme: String = "system",
    val dynamicColor: Boolean = true,
    val language: String = "system",
)
