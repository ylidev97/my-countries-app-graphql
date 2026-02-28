package com.lidev.mycountriesapp.ui.screens.countries

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lidev.mycountriesapp.domain.usecases.GetCountriesUseCase
import com.lidev.mycountriesapp.domain.usecases.GetCountryByCodeUseCase
import com.lidev.mycountriesapp.ui.screens.countries.model.CountriesScreenState
import com.lidev.mycountriesapp.ui.screens.countries.model.toUi
import com.lidev.mycountriesapp.util.NetworkMonitor
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CountriesScreenViewModel(
    private val getCountriesUseCase: GetCountriesUseCase,
    private val getCountryByCodeUseCase: GetCountryByCodeUseCase,
    networkMonitor: NetworkMonitor,
) : ViewModel() {
    private val _state = MutableStateFlow(CountriesScreenState())
    val state = _state.asStateFlow()

    init {
        networkMonitor.isOnline
            .onEach { isOnline ->
                _state.update {
                    it.copy(
                        isOnline = isOnline,
                        countries = if (!isOnline) persistentListOf() else it.countries,
                        selectedCountry = if (!isOnline) null else it.selectedCountry,
                    )
                }
                if (isOnline) {
                    getCountries()
                }
            }.launchIn(viewModelScope)
    }

    private suspend fun getCountries() {
        _state.update {
            it.copy(
                isLoading = true,
            )
        }
        val countriesResult = getCountriesUseCase()
        _state.update { screenState ->
            screenState.copy(
                countries =
                    countriesResult.getOrNull()?.map { it.toUi() }?.toPersistentList()
                        ?: persistentListOf(),
                isLoading = false,
            )
        }
    }

    fun onRetry() {
        viewModelScope.launch {
            getCountries()
        }
    }

    fun onSearchQueryChange(query: String) {
        _state.update { it.copy(searchQuery = query) }
    }

    fun selectCountry(code: String?) {
        viewModelScope.launch {
            if (code == null) {
                _state.update { it.copy(selectedCountry = null) }
                return@launch
            }

            _state.update { it.copy(isLoading = true, error = null) } // Clear previous errors

            getCountryByCodeUseCase(code)
                .onSuccess { countryDetail ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            selectedCountry =
                                countryDetail?.toUi(
                                    isFavorite = it.favoriteCountryCodes.contains(code),
                                ),
                        )
                    }
                }.onFailure { _ ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = "Failed to load country details.", // Or use throwable.message
                        )
                    }
                }
        }
    }

    fun toggleFavorite(code: String) {
        viewModelScope.launch {
            val currentFavorites = _state.value.favoriteCountryCodes.toMutableSet()
            val isCurrentlyFavorite = currentFavorites.contains(code)

            if (isCurrentlyFavorite) {
                currentFavorites.remove(code)
            } else {
                currentFavorites.add(code)
            }

            _state.update { currentState ->
                currentState.copy(
                    favoriteCountryCodes = currentFavorites.toPersistentList(),
                    countries =
                        currentState.countries
                            .map { country ->
                                // Avoid re-copying if the favorite status hasn't changed
                                if (country.code == code) {
                                    country.copy(isFavorite = !isCurrentlyFavorite)
                                } else {
                                    country
                                }
                            }.toPersistentList(),
                    selectedCountry =
                        currentState.selectedCountry?.let { selected ->
                            if (selected.code == code) {
                                selected.copy(isFavorite = !isCurrentlyFavorite)
                            } else {
                                selected
                            }
                        },
                )
            }
        }
    }
}
