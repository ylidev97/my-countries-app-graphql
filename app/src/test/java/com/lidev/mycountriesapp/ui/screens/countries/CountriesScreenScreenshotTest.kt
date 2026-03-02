package com.lidev.mycountriesapp.ui.screens.countries

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import com.github.takahirom.roborazzi.captureRoboImage
import com.lidev.mycountriesapp.ui.screens.countries.composables.CountriesScreenContent
import com.lidev.mycountriesapp.ui.screens.countries.model.CountryUi
import com.lidev.mycountriesapp.ui.theme.MyCountriesAppTheme
import kotlinx.collections.immutable.persistentListOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [33], qualifiers = "xxhdpi")
class CountriesScreenScreenshotTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun countriesScreen_loadedState() {
        composeTestRule.setContent {
            MyCountriesAppTheme {
                CountriesScreenContent(
                    countries =
                        persistentListOf(
                            CountryUi(
                                code = "CU",
                                name = "Cuba",
                                emoji = "🇨🇺",
                                continent = "North America",
                                isFavorite = true,
                            ),
                            CountryUi(
                                code = "ES",
                                name = "Spain",
                                emoji = "🇨🇺",
                                continent = "Europe",
                                isFavorite = false,
                            ),
                        ),
                    isLoading = true,
                    isOnline = true,
                    onItemClick = {},
                    onFavoriteClick = {},
                    searchQuery = "",
                    onSearchQueryChange = {},
                    onRetry = {},
                )
            }
        }
        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    fun countriesScreen_noInternetState() {
        composeTestRule.setContent {
            MyCountriesAppTheme {
                CountriesScreenContent(
                    countries = persistentListOf<CountryUi>(),
                    isLoading = false,
                    isOnline = false,
                    onItemClick = {},
                    onFavoriteClick = {},
                    searchQuery = "",
                    onSearchQueryChange = {},
                    onRetry = {},
                )
            }
        }
        composeTestRule.onRoot().captureRoboImage()
    }
}
