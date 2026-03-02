package com.lidev.mycountriesapp.domain.usecases

import com.lidev.mycountriesapp.domain.repository.SettingsRepository

class SetPaletteUseCase(
    private val repository: SettingsRepository,
) {
    suspend operator fun invoke(palette: String) = repository.setPalette(palette)
}
