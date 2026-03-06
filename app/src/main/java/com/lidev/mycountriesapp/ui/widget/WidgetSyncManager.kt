package com.lidev.mycountriesapp.ui.widget

import android.content.Context
import androidx.glance.appwidget.updateAll

class WidgetSyncManager(private val context: Context) {
    suspend fun updateWidgets() {
        MyAppWidget().updateAll(context)
    }
}
