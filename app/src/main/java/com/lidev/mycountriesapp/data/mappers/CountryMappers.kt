package com.lidev.mycountriesapp.data.mappers

import com.lidev.mycountriesapp.countriestrevorblades.GetCountriesQuery
import com.lidev.mycountriesapp.countriestrevorblades.GetCountryByCodeQuery
import com.lidev.mycountriesapp.domain.model.Country
import com.lidev.mycountriesapp.domain.model.CountryDetail
import com.lidev.mycountriesapp.domain.model.Language

internal fun GetCountriesQuery.Country.toDomain(): Country = Country(code = code, name = name, emoji = emoji)

internal fun GetCountryByCodeQuery.Country.toDomain(): CountryDetail =
    CountryDetail(
        code = code,
        name = name,
        emoji = emoji,
        currency = currency,
        languages = languages.map { it.toDomain() },
        phone = phone,
        capital = capital.orEmpty(),
        continent = continent.name,
    )

internal fun GetCountryByCodeQuery.Language.toDomain(): Language =
    Language(
        code = code,
        name = name,
        native = native,
    )
