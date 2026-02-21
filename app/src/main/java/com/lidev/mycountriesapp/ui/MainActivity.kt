package com.lidev.mycountriesapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.lidev.mycountriesapp.ui.screens.countries.composables.CountriesScreen
import com.lidev.mycountriesapp.ui.theme.MyCountriesAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyCountriesAppTheme {
              // throw Throwable()
                CountriesScreen()
            }
        }
    }
}

