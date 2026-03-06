package com.lidev.mycountriesapp.domain.usecases

import com.lidev.mycountriesapp.domain.NotificationScheduler
import com.lidev.mycountriesapp.domain.repository.SettingsRepository

class SetNotificationsEnabledUseCase(
    private val repository: SettingsRepository,
    private val notificationScheduler: NotificationScheduler,
) {
    suspend operator fun invoke(enabled: Boolean) {
        repository.setNotificationsEnabled(enabled)
        if (enabled) {
            notificationScheduler.schedule()
        } else {
            notificationScheduler.cancel()
        }
    }
}
