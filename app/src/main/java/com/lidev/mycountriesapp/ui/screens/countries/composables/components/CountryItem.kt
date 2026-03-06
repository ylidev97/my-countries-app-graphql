package com.lidev.mycountriesapp.ui.screens.countries.composables.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.lidev.mycountriesapp.ui.components.FavoriteToggle
import com.lidev.mycountriesapp.ui.theme.dimens

@Composable
internal fun CountryItem(
    isFavorite: Boolean = false,
    emoji: String,
    name: String,
    onItemClick: () -> Unit,
    onFavoriteClick: (Boolean) -> Unit,
) {
    OutlinedCard(
        modifier = Modifier.fillMaxWidth(),
        onClick = onItemClick,
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(all = MaterialTheme.dimens.small),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(emoji, fontSize = CountryItemDefault.EMOJI_FONT_SIZE)
            Spacer(
                modifier =
                    Modifier.width(
                        MaterialTheme.dimens.mediumSmall,
                    ),
            )
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    name,
                    style = MaterialTheme.typography.headlineMedium,
                    maxLines = CountryItemDefault.MAX_LINES_NAME,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            Spacer(modifier = Modifier.width(MaterialTheme.dimens.small))
            Box(contentAlignment = Alignment.CenterEnd) {
                FavoriteToggle(
                    isFavorite = isFavorite,
                    onToggle = onFavoriteClick,
                )
            }
        }
    }
}

@Composable
@Preview
private fun CountryItemPreview() {
    Column(
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.medium),
    ) {
        CountryItem(
            emoji = "\uD83C\uDDE6\uD83C\uDDE9",
            name = "name",
            onItemClick = {},
            onFavoriteClick = {},
        )
        CountryItem(
            emoji = "\uD83C\uDDE6\uD83C\uDDE9",
            name = "Lorem impsum large text when the test is large",
            onItemClick = {},
            onFavoriteClick = {},
        )
    }
}

private object CountryItemDefault {
    const val MAX_LINES_NAME = 2
    val EMOJI_FONT_SIZE = 40.sp
}
