package com.lidev.mycountriesapp.domain.model

sealed class AppTheme(
    val key: String,
) {
    data object System : AppTheme("system")

    data object Light : AppTheme("light")

    data object Dark : AppTheme("dark")

    companion object {
        fun fromKey(key: String): AppTheme =
            when (key) {
                Light.key -> Light
                Dark.key -> Dark
                else -> System
            }
    }
}
