package com.lidev.mycountriesapp.ui.screens.settings.composables.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.lidev.mycountriesapp.R

@Composable
fun PaletteSettings(
    selectedPalette: String,
    onPaletteSelected: (String) -> Unit,
) {
    val options =
        listOf(
            "default" to stringResource(R.string.palette_default),
            "nature" to stringResource(R.string.palette_nature),
        )

    options.forEachIndexed { index, (key, label) ->
        SettingsListItem(
            title = label,
            icon = Icons.Default.Palette,
            iconContainerColor =
                if (selectedPalette == key) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.surfaceVariant
                },
            iconContentColor =
                if (selectedPalette == key) {
                    MaterialTheme.colorScheme.onPrimary
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                },
            trailingContent = {
                RadioButton(
                    selected = (selectedPalette == key),
                    onClick = null,
                )
            },
            onClick = { onPaletteSelected(key) },
        )
        if (index < options.size - 1) {
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 64.dp, vertical = 4.dp),
                thickness = 0.5.dp,
                color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f),
            )
        }
    }
}
