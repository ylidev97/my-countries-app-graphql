package com.lidev.mycountriesapp.domain.usecases

import com.lidev.mycountriesapp.domain.model.AppPalette
import com.lidev.mycountriesapp.domain.repository.SettingsRepository

class SetPaletteUseCase(
    private val repository: SettingsRepository,
) {
    suspend operator fun invoke(palette: AppPalette) = repository.setPalette(palette)
}
