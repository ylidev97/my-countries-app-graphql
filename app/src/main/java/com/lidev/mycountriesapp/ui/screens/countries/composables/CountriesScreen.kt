package com.lidev.mycountriesapp.ui.screens.countries.composables

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.lidev.mycountriesapp.ui.components.LikeAnimation
import com.lidev.mycountriesapp.ui.screens.countries.CountriesScreenViewModel
import com.lidev.mycountriesapp.ui.screens.countries.composables.components.ContinentFilter
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

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
internal fun CountriesScreen(onSettingsClick: () -> Unit = {}) {
    val viewModel: CountriesScreenViewModel = koinViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val permissionState =
        rememberPermissionState(
            permission = Manifest.permission.POST_NOTIFICATIONS,
        )

    LaunchedEffect(Unit) {
        permissionState.launchPermissionRequest()
    }

    LaunchedEffect(permissionState.status) {
        viewModel.onNotificationEnable(permissionState.status.isGranted)
    }

    Content(
        countries = state.countries,
        countryDetail = state.selectedCountry,
        isOnline = state.isOnline,
        isOfflineMode = state.isOfflineMode,
        onDismissSheet = {
            viewModel.selectCountry(null)
        },
        isLoading = state.isLoading,
        onItemClick = viewModel::selectCountry,
        onFavoriteClick = viewModel::toggleFavorite,
        onSearchQueryChange = viewModel::onSearchQueryChange,
        searchQuery = state.searchQuery,
        onRetry = viewModel::onRetry,
        onSettingsClick = onSettingsClick,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Content(
    countries: ImmutableList<CountryUi> = persistentListOf(),
    isLoading: Boolean,
    isOnline: Boolean,
    isOfflineMode: Boolean,
    countryDetail: CountryDetailUi? = null,
    onDismissSheet: () -> Unit = {},
    onItemClick: (String) -> Unit,
    onFavoriteClick: (String) -> Unit,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onRetry: () -> Unit,
    onSettingsClick: () -> Unit = {},
) {
    var showLikeAnimation by remember { mutableStateOf(false) }
    var showSearchBar by remember { mutableStateOf(false) }
    val lazyListState = rememberLazyListState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    val canShowContent = isOnline || isOfflineMode

    val continents =
        remember(countries) {
            countries
                .map { it.continent }
                .distinct()
                .sorted()
                .toPersistentList()
        }
    var selectedContinent by remember { mutableStateOf<String?>(null) }

    val filteredCountries =
        remember(countries, searchQuery, selectedContinent) {
            countries
                .filter {
                    val matchesSearch = it.name.contains(searchQuery, ignoreCase = true)
                    val matchesContinent =
                        selectedContinent == null || it.continent == selectedContinent
                    matchesSearch && matchesContinent
                }.sortedBy { it.name }
                .toPersistentList()
        }

    LaunchedEffect(showLikeAnimation) {
        if (showLikeAnimation) {
            delay(CountriesScreenDefaults.DELAY_TIMER)
            showLikeAnimation = false
        }
    }

    Scaffold(
        modifier =
            Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            CountriesTopAppBar(
                showSearchBar = showSearchBar,
                onSearchBarToggle = { showSearchBar = it },
                searchQuery = searchQuery,
                onSearchQueryChange = onSearchQueryChange,
                isOnline = canShowContent,
                onSettingsClick = onSettingsClick,
                scrollBehavior = scrollBehavior,
            )
        },
    ) { innerPadding ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
        ) {
            if (canShowContent && continents.isNotEmpty()) {
                ContinentFilter(
                    continents = continents,
                    selectedContinent = selectedContinent,
                    onContinentSelected = { selectedContinent = it },
                )
            }

            Box(
                modifier = Modifier.weight(1f),
            ) {
                if (!canShowContent) {
                    NoInternetComp(
                        onRetry = onRetry,
                    )
                } else {
                    PullToRefreshBox(
                        isRefreshing = isLoading,
                        onRefresh = onRetry,
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        CountryList(
                            modifier =
                                Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = MaterialTheme.dimens.medium),
                            lazyListState = lazyListState,
                            filteredCountries = filteredCountries,
                            onItemClick = onItemClick,
                            isLoading = isLoading,
                            onFavoriteClick = {
                                showLikeAnimation = !it.isFavorite
                                onFavoriteClick(it.code)
                            },
                        )
                    }
                }

                this@Column.AnimatedVisibility(
                    modifier = Modifier.align(Alignment.Center),
                    visible = showLikeAnimation,
                    enter = scaleIn(spring()),
                    exit = fadeOut(),
                ) {
                    LikeAnimation()
                }
            }
        }
    }

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
                        continent = "North America",
                        isFavorite = false,
                    ),
                    CountryUi(
                        code = "CA",
                        name = "Canada",
                        emoji = "🇨🇦",
                        continent = "North America",
                        isFavorite = true,
                    ),
                    CountryUi(
                        code = "FR",
                        name = "France",
                        emoji = "🇫🇷",
                        continent = "Europe",
                        isFavorite = false,
                    ),
                    CountryUi(
                        code = "DE",
                        name = "Germany",
                        emoji = "🇩🇪",
                        continent = "Europe",
                        isFavorite = false,
                    ),
                ),
            isLoading = false,
            onItemClick = {},
            onFavoriteClick = {},
            searchQuery = "",
            onSearchQueryChange = {},
            isOnline = true,
            isOfflineMode = false,
            onRetry = {},
        )
    }
}

private object CountriesScreenDefaults {
    const val DELAY_TIMER: Long = 1000
}
