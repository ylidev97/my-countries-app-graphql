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
import com.lidev.mycountriesapp.domain.model.AppTheme
import com.lidev.mycountriesapp.ui.theme.MyIcons
import com.lidev.mycountriesapp.ui.theme.dimens

@Composable
fun ThemeSettings(
    selectedTheme: AppTheme,
    onThemeSelected: (AppTheme) -> Unit,
) {
    val options =
        listOf(
            Triple(
                AppTheme.System,
                stringResource(R.string.theme_system),
                MyIcons.settingsSystemDayDreamIcon,
            ),
            Triple(AppTheme.Light, stringResource(R.string.theme_light), MyIcons.lightModeIcon),
            Triple(AppTheme.Dark, stringResource(R.string.theme_dark), MyIcons.darkModeIcon),
        )

    options.forEachIndexed { index, (theme, label, icon) ->
        SettingsListItem(
            title = label,
            icon = icon,
            iconContainerColor =
                if (selectedTheme == theme) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.surfaceVariant
                },
            iconContentColor =
                if (selectedTheme == theme) {
                    MaterialTheme.colorScheme.onPrimary
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                },
            trailingContent = {
                RadioButton(
                    selected = (selectedTheme == theme),
                    onClick = null,
                )
            },
            onClick = { onThemeSelected(theme) },
        )
        if (index < options.lastIndex) {
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
