package com.lidev.mycountriesapp.domain.usecases

import com.lidev.mycountriesapp.domain.model.AppTheme
import com.lidev.mycountriesapp.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow

class GetThemeUseCase(
    private val repository: SettingsRepository,
) {
    operator fun invoke(): Flow<AppTheme> = repository.themeFlow
}
