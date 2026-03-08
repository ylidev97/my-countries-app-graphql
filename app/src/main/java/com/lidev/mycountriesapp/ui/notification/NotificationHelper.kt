package com.lidev.mycountriesapp.ui.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.lidev.mycountriesapp.R
import com.lidev.mycountriesapp.domain.model.Country
import com.lidev.mycountriesapp.ui.MainActivity

class NotificationHelper(
    private val context: Context,
) {
    companion object {
        private const val CHANNEL_ID = "country_of_the_day_channel"
        private const val NOTIFICATION_ID = 1001
    }

    fun showCountryNotification(country: Country) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        createNotificationChannel(notificationManager)

        val intent =
            Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }

        val pendingIntent =
            PendingIntent.getActivity(
                context,
                0,
                intent,
                PendingIntent.FLAG_IMMUTABLE,
            )

        val notification =
            NotificationCompat
                .Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_search)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText("${country.emoji} ${country.name} - ${country.continent}")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build()

        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    private fun createNotificationChannel(notificationManager: NotificationManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Country of the Day"
            val descriptionText = "Daily notification to learn about a new country"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel =
                NotificationChannel(CHANNEL_ID, name, importance).apply {
                    description = descriptionText
                }
            notificationManager.createNotificationChannel(channel)
        }
    }
}
