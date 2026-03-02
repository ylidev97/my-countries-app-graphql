package com.lidev.mycountriesapp.domain.usecases

import com.lidev.mycountriesapp.domain.repository.SettingsRepository

class SetDynamicColorUseCase(
    private val repository: SettingsRepository,
) {
    suspend operator fun invoke(enabled: Boolean) = repository.setDynamicColor(enabled)
}
