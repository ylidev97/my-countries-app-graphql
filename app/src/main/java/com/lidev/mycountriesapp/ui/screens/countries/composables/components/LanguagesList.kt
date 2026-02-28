package com.lidev.mycountriesapp.ui.screens.countries.composables.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.lidev.mycountriesapp.R
import com.lidev.mycountriesapp.ui.theme.dimens
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun LanguagesList(
    modifier: Modifier = Modifier,
    languages: ImmutableList<String> = persistentListOf(),
) {
    Column(modifier = modifier) {
        Text(
            stringResource(R.string.languages),
            style = MaterialTheme.typography.headlineSmall,
        )
        languages.forEach { language ->
            LanguageItem(language = language)
        }
    }
}

@Composable
private fun LanguageItem(
    modifier: Modifier = Modifier,
    language: String,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = "•", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.width(MaterialTheme.dimens.small))
        Text(
            text = language,
            style =
                MaterialTheme.typography.bodyMedium
                    .copy(
                        fontWeight = FontWeight.Light,
                    ),
        )
    }
}

@Preview
@Composable
private fun LanguagesListPreview() {
    LanguagesList(
        languages =
            persistentListOf(
                "Spanish",
                "Portuguese",
            ),
    )
}
