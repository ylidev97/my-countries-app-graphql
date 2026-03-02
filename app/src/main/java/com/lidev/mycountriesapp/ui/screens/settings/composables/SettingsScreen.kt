package com.lidev.mycountriesapp.ui.screens.settings.composables

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lidev.mycountriesapp.R
import com.lidev.mycountriesapp.ui.screens.settings.SettingsViewModel
import com.lidev.mycountriesapp.ui.screens.settings.composables.components.LanguageSettings
import com.lidev.mycountriesapp.ui.screens.settings.composables.components.PaletteSettings
import com.lidev.mycountriesapp.ui.screens.settings.composables.components.SettingsListItem
import com.lidev.mycountriesapp.ui.screens.settings.composables.components.SettingsSection
import com.lidev.mycountriesapp.ui.screens.settings.composables.components.ThemeSettings
import com.lidev.mycountriesapp.ui.screens.settings.model.SettingsState
import com.lidev.mycountriesapp.ui.theme.MyCountriesAppTheme
import com.lidev.mycountriesapp.ui.theme.MyIcons
import com.lidev.mycountriesapp.ui.theme.dimens
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBackClick: () -> Unit,
    viewModel: SettingsViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    SettingsContent(
        state = state,
        onBackClick = onBackClick,
        onThemeSelected = viewModel::setTheme,
        onPaletteSelected = viewModel::setPalette,
        onDynamicColorChanged = viewModel::setDynamicColor,
        onLanguageSelected = viewModel::setLanguage,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingsContent(
    state: SettingsState,
    onBackClick: () -> Unit,
    onThemeSelected: (String) -> Unit,
    onPaletteSelected: (String) -> Unit,
    onDynamicColorChanged: (Boolean) -> Unit,
    onLanguageSelected: (String) -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(
                        stringResource(R.string.settings),
                        style =
                            MaterialTheme.typography.headlineMedium.copy(
                                fontWeight = FontWeight.ExtraBold,
                            ),
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = MyIcons.arrowBackIcon,
                            contentDescription = stringResource(R.string.back),
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
                colors =
                    TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        scrolledContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                    ),
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
    ) { padding ->
        LazyColumn(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = MaterialTheme.dimens.large),
        ) {
            item { Spacer(modifier = Modifier.height(MaterialTheme.dimens.medium)) }

            item {
                SettingsSection(title = stringResource(R.string.theme)) {
                    ThemeSettings(
                        selectedTheme = state.theme,
                        onThemeSelected = onThemeSelected,
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(MaterialTheme.dimens.extraLarge)) }

            item {
                SettingsSection(title = stringResource(R.string.palette)) {
                    PaletteSettings(
                        selectedPalette = state.palette,
                        onPaletteSelected = onPaletteSelected,
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(MaterialTheme.dimens.extraLarge)) }

            item {
                SettingsSection(title = stringResource(R.string.dynamic_color)) {
                    SettingsListItem(
                        title = stringResource(R.string.dynamic_color),
                        subtitle = stringResource(R.string.dynamic_color_desc),
                        icon = MyIcons.colorLensIcon,
                        iconContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
                        iconContentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                        trailingContent = {
                            Switch(
                                checked = state.dynamicColor,
                                onCheckedChange = onDynamicColorChanged,
                            )
                        },
                        onClick = { onDynamicColorChanged(!state.dynamicColor) },
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(MaterialTheme.dimens.extraLarge)) }

            item {
                SettingsSection(title = stringResource(R.string.language)) {
                    LanguageSettings(
                        selectedLanguage = state.language,
                        onLanguageSelected = onLanguageSelected,
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(MaterialTheme.dimens.extraExtraLarge)) }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SettingsScreenPreview() {
    MyCountriesAppTheme {
        SettingsContent(
            state = SettingsState(),
            onBackClick = {},
            onThemeSelected = {},
            onPaletteSelected = {},
            onDynamicColorChanged = {},
            onLanguageSelected = {},
        )
    }
}
