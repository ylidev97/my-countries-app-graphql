package com.lidev.mycountriesapp.ui.notification

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.lidev.mycountriesapp.domain.usecases.GetCountriesUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.concurrent.TimeUnit

class DailyCountryWorker(
    context: Context,
    workerParams: WorkerParameters,
) : CoroutineWorker(context, workerParams),
    KoinComponent {
    private val getCountriesUseCase: GetCountriesUseCase by inject()

    override suspend fun doWork(): Result =
        try {
            val countriesResult = getCountriesUseCase()
            val country = countriesResult.getOrNull()?.randomOrNull()

            if (country != null) {
                NotificationHelper(applicationContext).showCountryNotification(country)
            }
            Result.success()
        } catch (_: Exception) {
            Result.failure()
        }

    companion object {
        private const val WORK_NAME = "DailyCountryWorker"

        fun schedule(context: Context) {
            val request =
                PeriodicWorkRequestBuilder<DailyCountryWorker>(24, TimeUnit.HOURS)
                    .setInitialDelay(
                        1,
                        TimeUnit.HOURS,
                    ) // Opcional: para que no suene justo al abrir la app la primera vez
                    .build()

            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                request,
            )
        }
    }
}
