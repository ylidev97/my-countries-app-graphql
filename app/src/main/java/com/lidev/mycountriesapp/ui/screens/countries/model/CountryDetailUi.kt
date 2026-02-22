package com.lidev.mycountriesapp.ui.screens.countries.model

import com.lidev.mycountriesapp.domain.model.CountryDetail
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

data class CountryDetailUi(
    val code: String,
    val name: String,
    val emoji: String, //Flag of the country
    val currency: String?,
    val languages: ImmutableList<String> = persistentListOf(),
    val phone: String, //International phone code,
    val continent: String,
    val capital: String,
    val isFavorite: Boolean = false,
)


internal fun CountryDetail.toUi(
    isFavorite: Boolean
): CountryDetailUi {
    return CountryDetailUi(
        code = code,
        name = name,
        emoji = emoji,
        currency = currency,
        languages = languages.map {
            it.name
        }.toPersistentList(),
        phone = phone,
        continent = continent,
        capital = capital,
        isFavorite = isFavorite
    )
}