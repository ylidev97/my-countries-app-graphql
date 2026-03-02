package com.lidev.mycountriesapp.domain.usecases

import com.lidev.mycountriesapp.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow

class GetLanguageUseCase(
    private val repository: SettingsRepository,
) {
    operator fun invoke(): Flow<String> = repository.languageFlow
}
