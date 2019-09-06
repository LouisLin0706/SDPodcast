package com.sdpodcast

import android.app.Application
import com.sdpodcast.di.appModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(appModel)
        }
    }
}