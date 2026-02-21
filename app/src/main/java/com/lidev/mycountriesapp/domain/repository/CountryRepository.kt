package com.lidev.mycountriesapp.domain.repository

import com.lidev.mycountriesapp.domain.model.Country
import com.lidev.mycountriesapp.domain.model.CountryDetail

interface CountryRepository {

    suspend fun getCountries(): Result<List<Country>>

    suspend fun getCountryByCode(code: String): Result<CountryDetail?>

}
