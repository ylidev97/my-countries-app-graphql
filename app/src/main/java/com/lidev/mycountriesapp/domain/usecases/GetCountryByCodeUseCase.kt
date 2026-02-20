package com.lidev.mycountriesapp.domain.usecases

import com.lidev.mycountriesapp.domain.model.CountryDetail
import com.lidev.mycountriesapp.domain.repository.CountryRepository

class GetCountryByCodeUseCase(
    private val repository: CountryRepository
) {

    suspend fun invoke(code: String): Result<CountryDetail> =
        repository.getCountryByCode(code)

}
