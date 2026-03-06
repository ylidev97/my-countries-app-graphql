package com.lidev.mycountriesapp.ui.screens.countries.composables.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.lidev.mycountriesapp.ui.screens.countries.model.CountryUi
import com.lidev.mycountriesapp.ui.theme.MyCountriesAppTheme
import com.lidev.mycountriesapp.ui.theme.dimens
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun CountryList(
    modifier: Modifier = Modifier,
    lazyListState: LazyListState,
    filteredCountries: ImmutableList<CountryUi>,
    onItemClick: (String) -> Unit,
    onFavoriteClick: (CountryUi) -> Unit,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        state = lazyListState,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.small),
    ) {
        items(filteredCountries, key = { it.code }) { countryItem ->
            CountryItem(
                isFavorite = countryItem.isFavorite,
                emoji = countryItem.emoji,
                name = countryItem.name,
                onItemClick = {
                    onItemClick(countryItem.code)
                },
                onFavoriteClick = {
                    onFavoriteClick(countryItem)
                },
            )
        }
        item {
            Spacer(
                modifier =
                    Modifier.height(
                        MaterialTheme.dimens.large,
                    ),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CountryListPreview() {
    MyCountriesAppTheme {
        CountryList(
            lazyListState = rememberLazyListState(),
            filteredCountries =
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
            onItemClick = {},
            onFavoriteClick = {},
        )
    }
}
