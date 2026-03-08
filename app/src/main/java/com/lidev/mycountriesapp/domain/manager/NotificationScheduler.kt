package com.lidev.mycountriesapp.domain.manager

interface NotificationScheduler {
    fun schedule()

    fun cancel()
}
