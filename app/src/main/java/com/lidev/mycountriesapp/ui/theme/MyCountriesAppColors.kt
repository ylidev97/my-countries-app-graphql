package com.lidev.mycountriesapp.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class MyCountriesAppColors(
    val favorite: Color = Color.Yellow,
    val favoriteEmpty: Color = Color.Gray,
    val white: Color = White,
    val black: Color = Black,
)

internal val LightColors =
    MyCountriesAppColors(
        // ...
    )

internal val DarkColors =
    MyCountriesAppColors(
        // ...
    )

internal val LocalColors: ProvidableCompositionLocal<MyCountriesAppColors> =
    compositionLocalOf { DarkColors }
