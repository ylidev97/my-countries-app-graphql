package com.lidev.mycountriesapp.domain.model

sealed class AppLanguage(
    val tag: String,
) {
    data object System : AppLanguage("system")

    data object English : AppLanguage("en")

    data object Spanish : AppLanguage("es")

    companion object {
        fun fromTag(tag: String): AppLanguage =
            when (tag) {
                English.tag -> English
                Spanish.tag -> Spanish
                else -> System
            }
    }
}