package com.lidev.mycountriesapp.ui.screens.countries.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lidev.mycountriesapp.ui.components.LikeAnimation
import com.lidev.mycountriesapp.ui.components.LoadingDialog
import com.lidev.mycountriesapp.ui.components.ScrollBubble
import com.lidev.mycountriesapp.ui.screens.countries.CountriesScreenViewModel
import com.lidev.mycountriesapp.ui.screens.countries.composables.components.CountriesTopAppBar
import com.lidev.mycountriesapp.ui.screens.countries.composables.components.CountryDetailSheet
import com.lidev.mycountriesapp.ui.screens.countries.composables.components.CountryList
import com.lidev.mycountriesapp.ui.screens.countries.composables.components.NoInternetComp
import com.lidev.mycountriesapp.ui.screens.countries.model.CountryDetailUi
import com.lidev.mycountriesapp.ui.screens.countries.model.CountryUi
import com.lidev.mycountriesapp.ui.theme.MyCountriesAppTheme
import com.lidev.mycountriesapp.ui.theme.dimens
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun CountriesScreen() {
    val viewModel: CountriesScreenViewModel = koinViewModel<CountriesScreenViewModel>()
    val state = viewModel.state.collectAsStateWithLifecycle()

    Content(
        countries = state.value.countries,
        countryDetail = state.value.selectedCountry,
        isOnline = state.value.isOnline,
        onDismissSheet = {
            viewModel.selectCountry(null)
        },
        isLoading = state.value.isLoading,
        onItemClick = viewModel::selectCountry,
        onFavoriteClick = viewModel::toggleFavorite,
        onSearchQueryChange = viewModel::onSearchQueryChange,
        searchQuery = state.value.searchQuery,
        onRetry = viewModel::onRetry,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Content(
    countries: ImmutableList<CountryUi> = persistentListOf(),
    isLoading: Boolean,
    isOnline: Boolean,
    countryDetail: CountryDetailUi? = null,
    onDismissSheet: () -> Unit = {},
    onItemClick: (String) -> Unit,
    onFavoriteClick: (String) -> Unit,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onRetry: () -> Unit,
) {
    var showLikeAnimation by remember { mutableStateOf(false) }
    var showSearchBar by remember { mutableStateOf(false) }
    val lazyListState = rememberLazyListState()
    val filteredCountries =
        remember(countries, searchQuery) {
            if (searchQuery.isBlank()) {
                countries
            } else {
                countries
                    .filter {
                        it.name.contains(searchQuery, ignoreCase = true)
                    }.sortedBy { it.name }
                    .toPersistentList()
            }
        }

    LaunchedEffect(showLikeAnimation) {
        if (showLikeAnimation) {
            delay(CountriesScreenDefaults.DELAY_TIMER)
            showLikeAnimation = false
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CountriesTopAppBar(
                showSearchBar = showSearchBar,
                onSearchBarToggle = { showSearchBar = it },
                searchQuery = searchQuery,
                onSearchQueryChange = onSearchQueryChange,
                isOnline = isOnline,
            )
        },
    ) { innerPadding ->
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
        ) {
            if (!isOnline) {
                NoInternetComp(
                    onRetry = onRetry,
                )
            } else {
                CountryList(
                    modifier = Modifier.fillMaxSize(),
                    lazyListState = lazyListState,
                    filteredCountries = filteredCountries,
                    onItemClick = onItemClick,
                    onFavoriteClick = {
                        showLikeAnimation = !it.isFavorite
                        onFavoriteClick(it.code)
                    },
                )
            }

            if (!showSearchBar && isOnline) {
                ScrollBubble(
                    modifier =
                        Modifier
                            .align(Alignment.TopEnd)
                            .padding(
                                end = MaterialTheme.dimens.small,
                                top = MaterialTheme.dimens.extraLarge,
                            ),
                    lazyListState = lazyListState,
                    firstLetters = filteredCountries.map { it.name.first() }.toPersistentList(),
                )
            }

            AnimatedVisibility(
                modifier = Modifier.align(Alignment.Center),
                visible = showLikeAnimation,
                enter = scaleIn(spring()),
                exit = fadeOut(),
            ) {
                LikeAnimation()
            }
        }
    }

    LoadingDialog(isLoading)

    CountryDetailSheet(
        countryDetail = countryDetail,
        onDismiss = onDismissSheet,
        onFavoriteToggle = {
            showLikeAnimation = it
            onFavoriteClick(countryDetail?.code.orEmpty())
        },
    )
}

@Preview
@Composable
private fun ContentPreview() {
    MyCountriesAppTheme {
        Content(
            countries =
                persistentListOf(
                    CountryUi(
                        code = "US",
                        name = "United States",
                        emoji = "🇺🇸",
                        isFavorite = false,
                    ),
                    CountryUi(
                        code = "CA",
                        name = "Canada",
                        emoji = "🇨🇦",
                        isFavorite = true,
                    ),
                    CountryUi(
                        code = "FR",
                        name = "France",
                        emoji = "🇫🇷",
                        isFavorite = false,
                    ),
                    CountryUi(
                        code = "DE",
                        name = "Germany",
                        emoji = "🇩🇪",
                        isFavorite = false,
                    ),
                ),
            isLoading = false,
            onItemClick = {},
            onFavoriteClick = {},
            searchQuery = "",
            onSearchQueryChange = {},
            isOnline = true,
            onRetry = {},
        )
    }
}

private object CountriesScreenDefaults {
    const val DELAY_TIMER: Long = 1000
}
