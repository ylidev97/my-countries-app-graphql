package com.lidev.mycountriesapp.ui.screens.countries.composables.components

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import com.github.takahirom.roborazzi.captureRoboImage
import com.lidev.mycountriesapp.ui.theme.MyCountriesAppTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [33], qualifiers = "xxhdpi")
class CountryItemScreenshotTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun countryItem_lightTheme() {
        composeTestRule.setContent {
            MyCountriesAppTheme(darkTheme = false) {
                CountryItem(
                    isFavorite = false,
                    emoji = "🇨🇺",
                    name = "Cuba",
                    onItemClick = {},
                    onFavoriteClick = {},
                )
            }
        }
        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    fun countryItem_darkTheme() {
        composeTestRule.setContent {
            MyCountriesAppTheme(darkTheme = true) {
                CountryItem(
                    isFavorite = true,
                    emoji = "🇨🇺",
                    name = "Cuba",
                    onItemClick = {},
                    onFavoriteClick = {},
                )
            }
        }
        composeTestRule.onRoot().captureRoboImage()
    }
}
