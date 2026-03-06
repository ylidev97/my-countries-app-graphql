package com.lidev.mycountriesapp.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Data class representing the standard dimensions (paddings, margins, spacings)
 * used across the app.
 */
@Immutable
data class Dimensions(
    /** Default spacing (0dp)**/
    val default: Dp = 0.dp,
    /** Extra-small spacing (4dp)**/
    val extraSmall: Dp = 4.dp,
    /** Small-small spacing (6dp)**/
    val smallSmall: Dp = 6.dp,
    /** Small spacing (8dp)**/
    val small: Dp = 8.dp,
    /** Medium-small spacing (12dp)**/
    val mediumSmall: Dp = 12.dp,
    /** Medium spacing (16dp)**/
    val medium: Dp = 16.dp,
    /** Large spacing (24dp)**/
    val large: Dp = 24.dp,
    /** Extra large spacing (32dp)**/
    val extraLarge: Dp = 32.dp,
    /** Extra, extra large spacing (64dp)**/
    val extraExtraLarge: Dp = 64.dp,
)

/**
 * CompositionLocal to provide [Dimensions] to the UI tree.
 */
internal val LocalDimensions = staticCompositionLocalOf { Dimensions() }
