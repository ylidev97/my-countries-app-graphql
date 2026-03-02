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
import com.lidev.mycountriesapp.domain.model.AppLanguage
import com.lidev.mycountriesapp.ui.theme.MyIcons
import com.lidev.mycountriesapp.ui.theme.dimens

@Composable
fun LanguageSettings(
    selectedLanguage: AppLanguage,
    onLanguageSelected: (AppLanguage) -> Unit,
) {
    val options =
        listOf(
            Triple(
                AppLanguage.System,
                stringResource(R.string.language_system),
                MyIcons.languageIcon,
            ),
            Triple(AppLanguage.English, "English", MyIcons.languageIcon),
            Triple(AppLanguage.Spanish, "Español", MyIcons.languageIcon),
        )

    options.forEachIndexed { index, (language, label, icon) ->
        SettingsListItem(
            title = label,
            icon = icon,
            iconContainerColor =
                if (selectedLanguage == language) {
                    MaterialTheme.colorScheme.secondary
                } else {
                    MaterialTheme.colorScheme.surfaceVariant
                },
            iconContentColor =
                if (selectedLanguage == language) {
                    MaterialTheme.colorScheme.onSecondary
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                },
            trailingContent = {
                RadioButton(
                    selected = (selectedLanguage == language),
                    onClick = null,
                )
            },
            onClick = { onLanguageSelected(language) },
        )
        if (index < options.lastIndex) {
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
