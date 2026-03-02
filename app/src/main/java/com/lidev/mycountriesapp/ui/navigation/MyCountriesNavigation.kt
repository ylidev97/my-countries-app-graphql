package com.lidev.mycountriesapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.lidev.mycountriesapp.ui.screens.countries.composables.CountriesScreen
import com.lidev.mycountriesapp.ui.screens.settings.composables.SettingsScreen

sealed class Screen(val route: String) {
    data object Countries : Screen("countries")
    data object Settings : Screen("settings")
}

@Composable
fun MyCountriesNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Countries.route,
        modifier = modifier,
    ) {
        composable(Screen.Countries.route) {
            CountriesScreen(
                onSettingsClick = { navController.navigate(Screen.Settings.route) },
            )
        }
        composable(Screen.Settings.route) {
            SettingsScreen(
                onBackClick = { navController.popBackStack() },
            )
        }
    }
}
