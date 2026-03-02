package com.lidev.mycountriesapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lidev.mycountriesapp.data.datasource.SettingsDataStore
import com.lidev.mycountriesapp.ui.screens.countries.composables.CountriesScreen
import com.lidev.mycountriesapp.ui.screens.settings.composables.SettingsScreen
import com.lidev.mycountriesapp.ui.theme.MyCountriesAppTheme
import org.koin.android.ext.android.inject
import java.util.Locale

class MainActivity : ComponentActivity() {
    private val settingsDataStore: SettingsDataStore by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val theme by settingsDataStore.themeFlow.collectAsState(initial = "system")
            val dynamicColor by settingsDataStore.dynamicColorFlow.collectAsState(initial = true)
            val language by settingsDataStore.languageFlow.collectAsState(initial = "system")

            val darkTheme =
                when (theme) {
                    "light" -> false
                    "dark" -> true
                    else -> isSystemInDarkTheme()
                }

            val context = LocalContext.current
            val locale =
                if (language == "system") {
                    Locale.getDefault()
                } else {
                    Locale(language)
                }

            val configuration = context.resources.configuration
            configuration.setLocale(locale)
            val localizedContext = context.createConfigurationContext(configuration)

            CompositionLocalProvider(LocalContext provides localizedContext) {
                MyCountriesAppTheme(
                    darkTheme = darkTheme,
                    dynamicColor = dynamicColor,
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "countries") {
                        composable("countries") {
                            CountriesScreen(
                                onSettingsClick = { navController.navigate("settings") },
                            )
                        }
                        composable("settings") {
                            SettingsScreen(
                                onBackClick = { navController.popBackStack() },
                            )
                        }
                    }
                }
            }
        }
    }
}
