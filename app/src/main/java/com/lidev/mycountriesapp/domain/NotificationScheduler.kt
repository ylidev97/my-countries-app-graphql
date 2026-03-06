package com.lidev.mycountriesapp.domain

interface NotificationScheduler {
    fun schedule()

    fun cancel()
}
