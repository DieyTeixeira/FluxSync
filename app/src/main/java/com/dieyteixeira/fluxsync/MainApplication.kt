package com.dieyteixeira.fluxsync

import android.app.Application
import com.dieyteixeira.fluxsync.app.di.koin.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Inicializar o Koin
        startKoin {
            androidContext(this@MainApplication)
            modules(appModule)
        }
    }
}