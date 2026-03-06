package com.lidev.mycountriesapp.domain.model

sealed class AppPalette(
    val key: String,
) {
    data object Default : AppPalette("default")

    data object Nature : AppPalette("nature")

    companion object {
        fun fromKey(key: String): AppPalette =
            when (key) {
                Nature.key -> Nature
                else -> Default
            }
    }
}
