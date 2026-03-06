package com.lidev.mycountriesapp.ui.screens.countries.model

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class CountriesScreenState(
    val isLoading: Boolean = false,
    val countries: ImmutableList<CountryUi> = persistentListOf(),
    val error: String? = null,
    val favoriteCountryCodes: ImmutableList<String> = persistentListOf(),
    val selectedCountry: CountryDetailUi? = null,
    val searchQuery: String = "",
    val isOnline: Boolean = false
)
