package com.aqeel.catsapp_kmm.android

import android.app.Application
import com.aqeel.catsapp_kmm.android.di.androidModule
import com.aqeel.catsapp_kmm.di.sharedModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CatApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@CatApp)
            modules(sharedModule, androidModule)
        }
    }
} 