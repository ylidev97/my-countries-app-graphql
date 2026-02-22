package com.lidev.mycountriesapp.ui.screens.countries.model

data class CountriesScreenState(
    val isLoading: Boolean = false,
    val countries: List<CountryUi> = emptyList(),
    val error: String? = null,
    val favoriteCountryCodes: List<String> = emptyList(),
    val selectedCountry: CountryDetailUi? = null,
    val searchQuery: String = "",
    val isOnline: Boolean = false
)
