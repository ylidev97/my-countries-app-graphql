package com.lidev.mycountriesapp.data.mappers.local

import com.lidev.mycountriesapp.data.database.entity.CountryEntity
import com.lidev.mycountriesapp.domain.model.Country
import com.lidev.mycountriesapp.domain.model.CountryDetail
import com.lidev.mycountriesapp.domain.model.Language

internal fun CountryEntity.toDomain(): Country =
    Country(
        code = code,
        name = name,
        emoji = emoji,
        continent = continent,
    )

internal fun Country.toEntity(): CountryEntity =
    CountryEntity(
        code = code,
        name = name,
        emoji = emoji,
        continent = continent,
    )

internal fun CountryEntity.toDetailDomain(): CountryDetail =
    CountryDetail(
        code = code,
        name = name,
        emoji = emoji,
        continent = continent,
        capital = capital.orEmpty(),
        currency = currency,
        phone = phone.orEmpty(),
        languages =
            languages?.split("|")?.map { langStr ->
                val parts = langStr.split(";")
                Language(
                    code = parts.getOrNull(0).orEmpty(),
                    name = parts.getOrNull(1).orEmpty(),
                    native = parts.getOrNull(2).orEmpty(),
                )
            } ?: emptyList(),
    )

internal fun CountryDetail.toEntity(): CountryEntity =
    CountryEntity(
        code = code,
        name = name,
        emoji = emoji,
        continent = continent,
        capital = capital,
        currency = currency,
        phone = phone,
        languages = languages.joinToString("|") { "${it.code};${it.name};${it.native}" },
    )
