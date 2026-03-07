package com.lidev.mycountriesapp.ui.screens.settings.composables

import android.Manifest
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.lidev.mycountriesapp.R
import com.lidev.mycountriesapp.domain.model.AppLanguage
import com.lidev.mycountriesapp.domain.model.AppPalette
import com.lidev.mycountriesapp.domain.model.AppTheme
import com.lidev.mycountriesapp.ui.screens.settings.SettingsViewModel
import com.lidev.mycountriesapp.ui.screens.settings.composables.components.LanguageSettings
import com.lidev.mycountriesapp.ui.screens.settings.composables.components.PaletteSettings
import com.lidev.mycountriesapp.ui.screens.settings.composables.components.SettingScreenTopAppBar
import com.lidev.mycountriesapp.ui.screens.settings.composables.components.SettingsListItem
import com.lidev.mycountriesapp.ui.screens.settings.composables.components.SettingsSection
import com.lidev.mycountriesapp.ui.screens.settings.composables.components.ThemeSettings
import com.lidev.mycountriesapp.ui.screens.settings.model.SettingsState
import com.lidev.mycountriesapp.ui.theme.MyCountriesAppTheme
import com.lidev.mycountriesapp.ui.theme.MyIcons
import com.lidev.mycountriesapp.ui.theme.dimens
import com.lidev.mycountriesapp.ui.util.MyCountriesIntent
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SettingsScreen(onBackClick: () -> Unit) {
    val viewModel: SettingsViewModel = koinViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    val permissionState =
        rememberPermissionState(
            permission = Manifest.permission.POST_NOTIFICATIONS,
        )

    SettingsContent(
        state = state,
        isEnableNotification = permissionState.status.isGranted,
        onBackClick = onBackClick,
        onThemeSelected = viewModel::setTheme,
        onPaletteSelected = viewModel::setPalette,
        onDynamicColorChanged = viewModel::setDynamicColor,
        onLanguageSelected = viewModel::setLanguage,
        onOfflineModeChanged = viewModel::setOfflineMode,
        onNotificationsEnabledChanged = { enabled ->
            if (!enabled) {
                MyCountriesIntent.openNotificationSettings(context)
            } else {
                if (permissionState.status.shouldShowRationale) {
                    permissionState.launchPermissionRequest()
                } else {
                    MyCountriesIntent.openNotificationSettings(context)
                }
            }
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingsContent(
    state: SettingsState,
    isEnableNotification: Boolean = false,
    onBackClick: () -> Unit,
    onThemeSelected: (AppTheme) -> Unit,
    onPaletteSelected: (AppPalette) -> Unit,
    onDynamicColorChanged: (Boolean) -> Unit,
    onLanguageSelected: (AppLanguage) -> Unit,
    onOfflineModeChanged: (Boolean) -> Unit,
    onNotificationsEnabledChanged: (Boolean) -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SettingScreenTopAppBar(
                onBackClick = onBackClick,
                scrollBehavior = scrollBehavior,
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

            item { Spacer(modifier = Modifier.height(MaterialTheme.dimens.large)) }

            item {
                SettingsSection(title = stringResource(R.string.palette)) {
                    PaletteSettings(
                        selectedPalette = state.palette,
                        onPaletteSelected = onPaletteSelected,
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(MaterialTheme.dimens.large)) }

            item {
                SettingsSection(title = stringResource(R.string.dynamic_color)) {
                    SettingsListItem(
                        title = stringResource(R.string.dynamic_color),
                        subtitle = stringResource(R.string.dynamic_color_desc),
                        icon = MyIcons.colorLensIcon,
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

            item { Spacer(modifier = Modifier.height(MaterialTheme.dimens.large)) }

            item {
                SettingsSection(title = stringResource(R.string.language)) {
                    LanguageSettings(
                        selectedLanguage = state.language,
                        onLanguageSelected = onLanguageSelected,
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(MaterialTheme.dimens.large)) }

            item {
                SettingsSection(title = stringResource(R.string.offline_mode)) {
                    SettingsListItem(
                        title = stringResource(R.string.offline_mode),
                        subtitle = stringResource(R.string.offline_mode_desc),
                        icon = MyIcons.cloudOffIcon,
                        trailingContent = {
                            Switch(
                                checked = state.offlineMode,
                                onCheckedChange = onOfflineModeChanged,
                            )
                        },
                        onClick = { onOfflineModeChanged(!state.offlineMode) },
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(MaterialTheme.dimens.large)) }

            item {
                SettingsSection(title = stringResource(R.string.notifications)) {
                    SettingsListItem(
                        title = stringResource(R.string.notifications),
                        subtitle = stringResource(R.string.notifications_desc),
                        icon = MyIcons.notificationsIcon,
                        trailingContent = {
                            Switch(
                                checked = isEnableNotification,
                                onCheckedChange = onNotificationsEnabledChanged,
                            )
                        },
                        onClick = { onNotificationsEnabledChanged(!isEnableNotification) },
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(MaterialTheme.dimens.large)) }

            item {
                SettingsSection(title = stringResource(R.string.about)) {
                    SettingsListItem(
                        title = stringResource(R.string.about),
                        subtitle = stringResource(R.string.about_desc, state.versionName),
                        icon = MyIcons.infoIcon,
                        onClick = { },
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
            onOfflineModeChanged = {},
            onNotificationsEnabledChanged = {},
        )
    }
}
