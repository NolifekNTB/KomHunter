package com.example.komhunter.core.di

import android.app.Application
import com.example.komhunter.uploadGPX.di.gpxModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class myApp: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidContext(this@myApp)
            modules(gpxModule)
        }
    }
}