package com.lidev.mycountriesapp.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.os.LocaleList
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.lidev.mycountriesapp.ui.navigation.MyCountriesNavHost
import com.lidev.mycountriesapp.ui.theme.MyCountriesAppTheme
import org.koin.androidx.compose.koinViewModel
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyCountriesApp()
        }
    }
}

@Composable
private fun MyCountriesApp(viewModel: MainViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val darkTheme =
        when (state.theme) {
            "light" -> false
            "dark" -> true
            else -> isSystemInDarkTheme()
        }

    val localizedContext = rememberLocalizedContext(language = state.language)

    CompositionLocalProvider(LocalContext provides localizedContext) {
        MyCountriesAppTheme(
            darkTheme = darkTheme,
            dynamicColor = state.dynamicColor,
        ) {
            val navController = rememberNavController()
            MyCountriesNavHost(navController = navController)
        }
    }
}

@SuppressLint("ObsoleteSdkInt", "LocalContextConfigurationRead")
@Composable
private fun rememberLocalizedContext(language: String): Context {
    val baseContext = LocalContext.current

    return remember(baseContext, language) {
        val locale =
            when (language) {
                "system" -> Locale.getDefault()
                else -> Locale.forLanguageTag(language)
            }

        val config = Configuration(baseContext.resources.configuration)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val localeList = LocaleList(locale)
            LocaleList.setDefault(localeList)
            config.setLocales(localeList)
        } else {
            config.setLocale(locale)
        }

        baseContext.createConfigurationContext(config)
    }
}
