@file:Suppress("MagicNumber")

package com.lidev.mycountriesapp.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

@Immutable
data class MyCountriesAppColors(
    val primary: Color = Color(0xFF6200EE),
    val primaryVariant: Color = Color(0xFF3700B3),
    val secondary: Color = Color(0xFF03DAC6),
    val background: Color = Color(0xFFFFFFFF),
    val surface: Color = Color(0xFFFFFFFF),
    val error: Color = Color(0xFFB00020),
    val onPrimary: Color = Color(0xFFFFFFFF),
    val onSecondary: Color = Color(0xFF000000),
    val onBackground: Color = Color(0xFF000000),
    val onSurface: Color = Color(0xFF000000),
    val onError: Color = Color(0xFFFFFFFF),
    val favorite: Color = Color(0xFFFF4081),
    val favoriteEmpty: Color = Color(0xFF757575),
    val success: Color = Color(0xFF4CAF50),
    val warning: Color = Color(0xFFFFC107),
)

val LocalColors: ProvidableCompositionLocal<MyCountriesAppColors> =
    compositionLocalOf { MyCountriesAppColors() }
