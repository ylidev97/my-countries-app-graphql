package com.lidev.mycountriesapp.ui.screens.countries.composables.components

import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lidev.mycountriesapp.R
import com.lidev.mycountriesapp.domain.model.CountryDetail
import com.lidev.mycountriesapp.ui.components.FavoriteToggle
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryDetailSheet(
    countryDetail: CountryDetail? = null,
    onFavoriteToggle: (Boolean) -> Unit,
    onDismiss: () -> Unit
) {
    countryDetail?.let {
        ModalBottomSheet(onDismissRequest = onDismiss,
            sheetGesturesEnabled = false,
            ) {
            CountryDetail(
                countryName = countryDetail.name,
                isFavorite = countryDetail.isFavorite,
                onFavoriteToggle = onFavoriteToggle,
                emoji = countryDetail.emoji,
                phoneCode = countryDetail.phone,
                continent = countryDetail.continent,
                languages = countryDetail.languages.map {
                    it.name
                }.toPersistentList()
            )
        }
    }

}

@Composable
internal fun CountryDetail(
    modifier: Modifier = Modifier,
    countryName: String = "Cuba",
    emoji: String = "🇨🇺",
    phoneCode: String = "53",
    continent: String = "North America",
    languages: ImmutableList<String> = persistentListOf(),
    isFavorite: Boolean = false,
    onFavoriteToggle: (Boolean) -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .padding(bottom = 12.dp),
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            FavoriteToggle(
                isFavorite = isFavorite,
                onToggle = onFavoriteToggle,
                modifier = Modifier.align(Alignment.TopEnd)
            )
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                //emoji
                Text(emoji, fontSize = 80.0.sp)
                //countryName
                Text(
                    text = countryName,
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.basicMarquee(
                        iterations = 20
                    )
                )
            }
        }

        HorizontalDivider(modifier = Modifier.padding(top = 4.dp, bottom = 8.dp))

        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            DetailRow(
                title = stringResource(id = R.string.phone),
                value = "+($phoneCode)"
            )

            DetailRow(
                title = stringResource(id = R.string.continent),
                value = continent
            )

            if (languages.isNotEmpty()) {
                //Languages
                LanguagesList(
                    modifier = Modifier.fillMaxWidth(),
                    languages = languages
                )
            }
        }
    }
}

@Composable
private fun DetailRow(
    modifier: Modifier = Modifier,
    title: String,
    value: String
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$title: ",
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 22.sp,
                fontWeight = FontWeight.Light
            )
        )
    }
}

@Preview
@Composable
private fun CountryDetailPreview() {
    CountryDetail(
        countryName = "The United States of America",
        languages = persistentListOf(
            "Spanish", "French"
        )
    )
}
