package com.lidev.mycountriesapp.ui.screens.settings.composables.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.lidev.mycountriesapp.R
import com.lidev.mycountriesapp.ui.theme.MyIcons
import com.lidev.mycountriesapp.ui.theme.dimens

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
            icon = MyIcons.paletteIcon,
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
                modifier =
                    Modifier.padding(
                        horizontal = MaterialTheme.dimens.extraExtraLarge,
                        vertical = MaterialTheme.dimens.extraSmall,
                    ),
                thickness = 0.5.dp,
                color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f),
            )
        }
    }
}
