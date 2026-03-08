package com.lidev.mycountriesapp.ui.manager

import android.content.Context
import com.lidev.mycountriesapp.domain.manager.NotificationScheduler
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
