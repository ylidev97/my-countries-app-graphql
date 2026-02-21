package com.lidev.mycountriesapp.ui.screens.countries.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lidev.mycountriesapp.domain.model.Country
import com.lidev.mycountriesapp.ui.components.LoadingDialog
import com.lidev.mycountriesapp.ui.screens.countries.CountriesScreenViewModel
import com.lidev.mycountriesapp.ui.screens.countries.composables.components.CountryItem
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun CountriesScreen() {
    val viewModel: CountriesScreenViewModel = koinViewModel<CountriesScreenViewModel>()
    val state = viewModel.state.collectAsStateWithLifecycle()

    Content(
        countries = state.value.countries.toPersistentList(),
        favoriteCountryCodes = state.value.favoriteCountryCodes.toPersistentList(),
        isLoading = state.value.isLoading,
        onItemClick = viewModel::selectCountry,
        onFavoriteClick = viewModel::toggleFavorite
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Content(
    countries: ImmutableList<Country> = persistentListOf(),
    favoriteCountryCodes: ImmutableList<String> = persistentListOf(),
    isLoading: Boolean,
    onItemClick: (String) -> Unit,
    onFavoriteClick: (Boolean, String) -> Unit,
) {


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
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = rememberLazyListState(),
            contentPadding = innerPadding,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(countries, key = { it.code }) { countryItem ->
                CountryItem(
                    isFavorite = favoriteCountryCodes.contains(countryItem.code),
                    emoji = countryItem.emoji,
                    name = countryItem.name,
                    onItemClick = {
                        onItemClick(countryItem.code)
                    },
                    onFavoriteClick = {
                        onFavoriteClick(it, countryItem.code)
                    }
                )
            }
        }
    }

    if (isLoading) {
        LoadingDialog()
    }
}


@Composable
@Preview
private fun CountriesScreenPreview() {
    CountriesScreen()
}
