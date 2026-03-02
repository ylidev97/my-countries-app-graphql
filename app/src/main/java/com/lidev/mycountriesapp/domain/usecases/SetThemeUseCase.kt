package com.lidev.mycountriesapp.domain.usecases

import com.lidev.mycountriesapp.domain.repository.SettingsRepository

class SetThemeUseCase(
    private val repository: SettingsRepository,
) {
    suspend operator fun invoke(theme: String) = repository.setTheme(theme)
}
