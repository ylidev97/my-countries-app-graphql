package com.lidev.mycountriesapp.ui.screens.countries.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lidev.mycountriesapp.domain.model.CountryDetail
import com.lidev.mycountriesapp.ui.components.LikeAnimation
import com.lidev.mycountriesapp.ui.components.LoadingDialog
import com.lidev.mycountriesapp.ui.screens.countries.CountriesScreenViewModel
import com.lidev.mycountriesapp.ui.screens.countries.composables.components.CountryDetailSheet
import com.lidev.mycountriesapp.ui.screens.countries.composables.components.CountryItem
import com.lidev.mycountriesapp.ui.screens.model.CountryUi
import com.lidev.mycountriesapp.ui.theme.MyCountriesAppTheme
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
        countries = state.value.countries.toPersistentList(),
        countryDetail = state.value.selectedCountry,
        onDismissSheet = {
            viewModel.selectCountry(null)
        },
        isLoading = state.value.isLoading,
        onItemClick = viewModel::selectCountry,
        onFavoriteClick = viewModel::toggleFavorite
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Content(
    countries: ImmutableList<CountryUi> = persistentListOf(),
    isLoading: Boolean,
    countryDetail: CountryDetail? = null,
    onDismissSheet: () -> Unit = {},
    onItemClick: (String) -> Unit,
    onFavoriteClick: (String) -> Unit,
) {

    var showLikeAnimation by remember { mutableStateOf(false) }

    LaunchedEffect(showLikeAnimation) {
        if (showLikeAnimation) {
            delay(1000)
            showLikeAnimation = false
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Countries")
                },
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = rememberLazyListState(),
                contentPadding = innerPadding,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(countries, key = { it.code }) { countryItem ->
                    CountryItem(
                        isFavorite = countryItem.isFavorite,
                        emoji = countryItem.emoji,
                        name = countryItem.name,
                        onItemClick = {
                            onItemClick(countryItem.code)
                        },
                        onFavoriteClick = {
                            showLikeAnimation = !countryItem.isFavorite
                            onFavoriteClick(countryItem.code)
                        }
                    )
                }
            }

            AnimatedVisibility(
                modifier = Modifier.align(Alignment.Center),
                visible = showLikeAnimation,
                enter = scaleIn(spring()),
                exit = fadeOut()
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
        }
    )
}

@Preview
@Composable
private fun ContentPreview() {
    MyCountriesAppTheme {
        Content(
            countries = persistentListOf(
                CountryUi(
                    code = "US",
                    name = "United States",
                    emoji = "🇺🇸",
                    isFavorite = false
                ),
                CountryUi(
                    code = "CA",
                    name = "Canada",
                    emoji = "🇨🇦",
                    isFavorite = true
                )
            ),
            isLoading = false,
            onItemClick = {},
            onFavoriteClick = {}
        )
    }
}
