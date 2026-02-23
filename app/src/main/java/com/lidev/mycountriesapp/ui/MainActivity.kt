package com.lidev.mycountriesapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.lidev.mycountriesapp.ui.screens.countries.composables.CountriesScreen
import com.lidev.mycountriesapp.ui.theme.MyCountriesAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyCountriesAppTheme {
                CountriesScreen()
            }
        }
    }
}

