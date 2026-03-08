package com.lidev.mycountriesapp.ui.screens.countries.composables.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.lidev.mycountriesapp.R
import com.lidev.mycountriesapp.ui.theme.MyCountriesAppTheme
import com.lidev.mycountriesapp.ui.theme.dimens
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun ContinentFilter(
    continents: ImmutableList<String>,
    selectedContinent: String?,
    onContinentSelected: (String?) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyRow(
        modifier =
            modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface),
        contentPadding =
            PaddingValues(
                horizontal = MaterialTheme.dimens.medium,
                vertical = MaterialTheme.dimens.small,
            ),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.small),
    ) {
        item {
            FilterChip(
                selected = selectedContinent == null,
                onClick = { onContinentSelected(null) },
                label = {
                    Text(
                        text = stringResource(R.string.all),
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Medium),
                    )
                },
                shape = CircleShape,
                colors =
                    FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        containerColor =
                            MaterialTheme.colorScheme.surfaceVariant.copy(
                                alpha = 0.5f,
                            ),
                        labelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    ),
                border = null,
            )
        }
        items(continents) { continent ->
            FilterChip(
                selected = selectedContinent == continent,
                onClick = {
                    onContinentSelected(if (selectedContinent == continent) null else continent)
                },
                label = {
                    Text(
                        text = continent,
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Medium),
                    )
                },
                shape = CircleShape,
                colors =
                    FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        containerColor =
                            MaterialTheme.colorScheme.surfaceVariant.copy(
                                alpha = 0.5f,
                            ),
                        labelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    ),
                border = null,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ContinentFilterPreview() {
    MyCountriesAppTheme {
        ContinentFilter(
            continents = persistentListOf("Africa", "Americas", "Asia", "Europe", "Oceania"),
            selectedContinent = "Europe",
            onContinentSelected = {},
        )
    }
}
