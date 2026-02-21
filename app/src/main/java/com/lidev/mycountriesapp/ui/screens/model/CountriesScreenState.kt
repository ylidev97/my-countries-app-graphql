package com.lidev.mycountriesapp.ui.screens.model

import com.lidev.mycountriesapp.domain.model.CountryDetail

data class CountriesScreenState(
    val isLoading: Boolean = false,
    val countries: List<CountryUi> = emptyList(),
    val error: String? = null,
    val favoriteCountryCodes: List<String> = emptyList(),
    val selectedCountry: CountryDetail? = null
)
