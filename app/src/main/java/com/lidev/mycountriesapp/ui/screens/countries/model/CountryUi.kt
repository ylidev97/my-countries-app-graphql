package com.lidev.mycountriesapp.ui.screens.countries.model

import com.lidev.mycountriesapp.domain.model.Country

data class CountryUi(
    val code: String,
    val name: String,
    val emoji: String, // Flag of the country,
    val continent: String,
    val isFavorite: Boolean = false,
)

internal fun Country.toUi(isFavorite: Boolean = false): CountryUi =
    CountryUi(
        code = code,
        name = name,
        emoji = emoji,
        continent = continent,
        isFavorite = isFavorite,
    )
