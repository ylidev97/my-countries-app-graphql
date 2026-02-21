package com.lidev.mycountriesapp

import android.app.Application
import com.lidev.mycountriesapp.data.id.dataModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class MyCountriesApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MyCountriesApp)
            modules(
                appModule,
                dataModule
            )
        }
    }

}
