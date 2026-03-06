package com.lidev.mycountriesapp.ui

import android.content.Context
import com.lidev.mycountriesapp.domain.NotificationScheduler
import com.lidev.mycountriesapp.ui.notification.DailyCountryWorker

class WorkManagerNotificationScheduler(
    private val context: Context,
) : NotificationScheduler {
    override fun schedule() {
        DailyCountryWorker.schedule(context)
    }

    override fun cancel() {
        DailyCountryWorker.cancel(context)
    }
}
