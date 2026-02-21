package com.lidev.mycountriesapp.ui.screens.countries

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lidev.mycountriesapp.domain.usecases.GetCountriesUseCase
import com.lidev.mycountriesapp.domain.usecases.GetCountryByCodeUseCase
import com.lidev.mycountriesapp.ui.screens.model.CountriesScreenState
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
        _state.update {
            it.copy(
                countries = countriesResult.getOrNull() ?: emptyList(),
                isLoading = false
            )
        }

    }

    fun selectCountry(code: String) {

    }

    fun toggleFavorite(isFavorite: Boolean, code: String) {
        viewModelScope.launch {
            val favoriteCode = _state.value.favoriteCountryCodes
            val favoriteCodeTemp = mutableSetOf<String>()
            favoriteCodeTemp.addAll(favoriteCode.toSet())

            if (favoriteCodeTemp.contains(code)) {
                favoriteCodeTemp.remove(code)
            } else {
                favoriteCodeTemp.add(code)
            }

            _state.update {
                it.copy(
                    favoriteCountryCodes = favoriteCodeTemp.toList()
                )
            }
        }
    }


}