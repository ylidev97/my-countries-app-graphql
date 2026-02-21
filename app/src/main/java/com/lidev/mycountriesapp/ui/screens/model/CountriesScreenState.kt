package com.lidev.mycountriesapp.ui.screens.model

import com.lidev.mycountriesapp.domain.model.Country

data class CountriesScreenState(
    val isLoading: Boolean = false,
    val countries: List<Country> = emptyList(),
    val error: String? = null,
    val favoriteCountryCodes: List<String> = emptyList()
)
