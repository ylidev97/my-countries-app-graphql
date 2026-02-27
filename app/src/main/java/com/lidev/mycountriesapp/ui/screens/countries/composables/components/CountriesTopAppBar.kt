package com.lidev.mycountriesapp.ui.screens.countries.composables.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.lidev.mycountriesapp.R
import com.lidev.mycountriesapp.ui.theme.MyCountriesAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CountriesTopAppBar(
    showSearchBar: Boolean,
    isOnline: Boolean = true,
    onSearchBarToggle: (Boolean) -> Unit,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
) {
    AnimatedContent(
        targetState = showSearchBar,
        label = "TopAppBar animation",
        transitionSpec = {
            if (targetState) {
                (slideInHorizontally { width -> width } + fadeIn())
                    .togetherWith(slideOutHorizontally { width -> -width } + fadeOut())
            } else {
                (slideInHorizontally { width -> -width } + fadeIn())
                    .togetherWith(slideOutHorizontally { width -> width } + fadeOut())
            }.using(
                SizeTransform(clip = false),
            )
        },
    ) { isSearchVisible ->
        if (isSearchVisible) {
            SearchTopAppBar(
                searchQuery = searchQuery,
                onSearchQueryChange = onSearchQueryChange,
                onCloseSearch = { onSearchBarToggle(false) },
            )
        } else {
            RegularTopAppBar(
                onOpenSearch = { onSearchBarToggle(true) },
                searchIconEnable = isOnline,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RegularTopAppBar(
    searchIconEnable: Boolean,
    onOpenSearch: () -> Unit,
) {
    CenterAlignedTopAppBar(
        title = { Text(text = stringResource(R.string.countries)) },
        actions = {
            IconButton(onClick = onOpenSearch, enabled = searchIconEnable) {
                SearchIcon()
            }
        },
    )
}

@Composable
private fun SearchIcon() {
    Icon(
        painter = painterResource(id = R.drawable.ic_search),
        contentDescription = "Search",
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchTopAppBar(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onCloseSearch: () -> Unit,
) {
    CenterAlignedTopAppBar(
        title = {
            TextField(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                placeholder = { Text(stringResource(R.string.search_countries)) },
                leadingIcon = {
                    SearchIcon()
                },
                trailingIcon = {
                    IconButton(onClick = {
                        if (searchQuery.isNotEmpty()) {
                            onSearchQueryChange("")
                        } else {
                            onCloseSearch()
                        }
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_close),
                            contentDescription = stringResource(R.string.close),
                        )
                    }
                },
                colors =
                    TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
            )
        },
    )
}

@Preview
@Composable
private fun CountriesTopAppBarPreview() {
    MyCountriesAppTheme {
        CountriesTopAppBar(
            showSearchBar = false,
            onSearchBarToggle = {},
            searchQuery = "",
            onSearchQueryChange = {},
        )
    }
}

@Preview
@Composable
private fun CountriesTopAppBarSearchPreview() {
    MyCountriesAppTheme {
        CountriesTopAppBar(
            showSearchBar = true,
            onSearchBarToggle = {},
            searchQuery = "test",
            onSearchQueryChange = {},
        )
    }
}
