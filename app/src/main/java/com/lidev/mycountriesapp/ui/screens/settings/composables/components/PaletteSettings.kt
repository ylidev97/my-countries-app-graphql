package com.lidev.mycountriesapp.ui.screens.settings.composables.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.lidev.mycountriesapp.R
import com.lidev.mycountriesapp.domain.model.AppPalette
import com.lidev.mycountriesapp.ui.theme.MyCountriesAppTheme
import com.lidev.mycountriesapp.ui.theme.MyIcons
import com.lidev.mycountriesapp.ui.theme.dimens

@Composable
fun PaletteSettings(
    selectedPalette: AppPalette,
    onPaletteSelected: (AppPalette) -> Unit,
) {
    val options =
        listOf(
            stringResource(R.string.palette_default) to AppPalette.Default,
            stringResource(R.string.palette_nature) to AppPalette.Nature,
        )

    Column {
        options.forEachIndexed { index, (label, palette) ->
            SettingsListItem(
                title = label,
                icon = MyIcons.paletteIcon,
                iconContainerColor =
                    if (selectedPalette == palette) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.surfaceVariant
                    },
                iconContentColor =
                    if (selectedPalette == palette) {
                        MaterialTheme.colorScheme.onPrimary
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    },
                trailingContent = {
                    RadioButton(
                        selected = (selectedPalette == palette),
                        onClick = null,
                    )
                },
                onClick = { onPaletteSelected(palette) },
            )
            if (index < options.size - 1) {
                HorizontalDivider(
                    modifier =
                        Modifier.padding(
                            horizontal = MaterialTheme.dimens.extraExtraLarge,
                            vertical = MaterialTheme.dimens.extraSmall,
                        ),
                    thickness = MaterialTheme.dimens.extraSmall / 8, // 0.5.dp
                    color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PaletteSettingsPreview() {
    MyCountriesAppTheme {
        Surface {
            PaletteSettings(
                selectedPalette = AppPalette.Default,
                onPaletteSelected = {},
            )
        }
    }
}
