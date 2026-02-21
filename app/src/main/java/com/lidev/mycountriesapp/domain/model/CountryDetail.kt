package com.lidev.mycountriesapp.domain.model

data class CountryDetail(
    val code: String,
    val name: String,
    val emoji: String, //Flag of the country
    val currency: String?,
    val languages: List<Language>,
    val phone: String, //International phone code,
    val continent: String,
    val capital: String,
    val isFavorite: Boolean = false,
)

data class Language(
    val code: String,
    val name: String,
    val native: String,
)

