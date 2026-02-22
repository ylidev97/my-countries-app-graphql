package com.lidev.mycountriesapp.domain.usecases

import com.lidev.mycountriesapp.domain.model.Country
import com.lidev.mycountriesapp.domain.repository.CountryRepository

class GetCountriesUseCase(
    private val repository: CountryRepository
) {

    suspend operator fun invoke(): Result<List<Country>> {
        return repository.getCountries().map {
            // Sort countries by name
            it.sortedBy { country -> country.name }
        }
    }

}