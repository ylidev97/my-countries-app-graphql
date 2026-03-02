package com.lidev.mycountriesapp.ui.screens.settings.composables.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.lidev.mycountriesapp.R

@Composable
fun LanguageSettings(
    selectedLanguage: String,
    onLanguageSelected: (String) -> Unit,
) {
    val options =
        listOf(
            "system" to stringResource(R.string.language_system),
            "en" to "English",
            "es" to "Español",
        )

    options.forEachIndexed { index, (key, label) ->
        SettingsListItem(
            title = label,
            icon = Icons.Default.Language,
            iconContainerColor =
                if (selectedLanguage == key) {
                    MaterialTheme.colorScheme.secondary
                } else {
                    MaterialTheme.colorScheme.surfaceVariant
                },
            iconContentColor =
                if (selectedLanguage == key) {
                    MaterialTheme.colorScheme.onSecondary
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                },
            trailingContent = {
                RadioButton(
                    selected = (selectedLanguage == key),
                    onClick = null,
                )
            },
            onClick = { onLanguageSelected(key) },
        )
        if (index < options.size - 1) {
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                thickness = 0.5.dp,
                color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f),
            )
        }
    }
}
