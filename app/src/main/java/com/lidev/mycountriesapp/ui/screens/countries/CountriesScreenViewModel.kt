package com.lidev.mycountriesapp.ui.screens.countries

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lidev.mycountriesapp.domain.usecases.GetCountriesUseCase
import com.lidev.mycountriesapp.domain.usecases.GetCountryByCodeUseCase
import com.lidev.mycountriesapp.ui.screens.countries.model.CountriesScreenState
import com.lidev.mycountriesapp.ui.screens.countries.model.toUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CountriesScreenViewModel(
    private val getCountriesUseCase: GetCountriesUseCase,
    private val getCountryByCodeUseCase: GetCountryByCodeUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CountriesScreenState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }
            getCountries()
        }
    }

    private suspend fun getCountries() {
        val countriesResult = getCountriesUseCase.invoke()
        _state.update { screenState ->
            screenState.copy(
                countries = countriesResult.getOrNull()?.map { it.toUi() } ?: emptyList(),
                isLoading = false
            )
        }

    }

    fun onSearchQueryChange(query: String) {
        _state.update { it.copy(searchQuery = query) }
    }

    fun selectCountry(code: String?) {
        viewModelScope.launch {
            if (code == null) {
                _state.update {
                    it.copy(
                        selectedCountry = null
                    )
                }
            } else {
                _state.update {
                    it.copy(
                        isLoading = true
                    )
                }
                val countryDetailResult = getCountryByCodeUseCase.invoke(code)
                _state.update {
                    it.copy(
                        isLoading = false,
                        selectedCountry = countryDetailResult.getOrNull()?.copy(
                            isFavorite = _state.value.favoriteCountryCodes.contains(code)
                        )
                    )
                }
            }
        }
    }

    fun toggleFavorite(code: String) {
        viewModelScope.launch {
            val favoriteCode = _state.value.favoriteCountryCodes
            val favoriteCodeTemp = mutableSetOf<String>()
            favoriteCodeTemp.addAll(favoriteCode.toSet())

            if (favoriteCodeTemp.contains(code)) {
                favoriteCodeTemp.remove(code)
            } else {
                favoriteCodeTemp.add(code)
            }

            _state.update { currentState ->
                currentState.copy(
                    favoriteCountryCodes = favoriteCodeTemp.toList(),
                    countries = currentState.countries.map {
                        it.copy(isFavorite = it.code in favoriteCodeTemp)
                    },
                    selectedCountry = currentState.selectedCountry?.copy(isFavorite = code in favoriteCodeTemp)
                )
            }
        }
    }


}