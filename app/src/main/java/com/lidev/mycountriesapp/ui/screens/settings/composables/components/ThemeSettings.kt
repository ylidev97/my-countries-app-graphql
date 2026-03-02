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
fun ThemeSettings(
    selectedTheme: String,
    onThemeSelected: (String) -> Unit,
) {
    val options =
        listOf(
            Triple(
                "system",
                stringResource(R.string.theme_system),
                MyIcons.settingsSystemDayDreamIcon,
            ),
            Triple("light", stringResource(R.string.theme_light), MyIcons.lightModeIcon),
            Triple("dark", stringResource(R.string.theme_dark), MyIcons.darkModeIcon),
        )

    options.forEachIndexed { index, (key, label, icon) ->
        SettingsListItem(
            title = label,
            icon = icon,
            iconContainerColor =
                if (selectedTheme == key) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.surfaceVariant
                },
            iconContentColor =
                if (selectedTheme == key) {
                    MaterialTheme.colorScheme.onPrimary
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                },
            trailingContent = {
                RadioButton(
                    selected = (selectedTheme == key),
                    onClick = null,
                )
            },
            onClick = { onThemeSelected(key) },
        )
        if (index < options.size - 1) {
            HorizontalDivider(
                modifier =
                    Modifier.padding(
                        horizontal = MaterialTheme.dimens.medium,
                        vertical = MaterialTheme.dimens.extraSmall,
                    ),
                thickness = 0.5.dp,
                color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f),
            )
        }
    }
}
