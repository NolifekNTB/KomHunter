package com.example.komhunter.core.di

import android.app.Application
import com.example.komhunter.uploadGPX.di.gpxModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.osmdroid.config.Configuration

class myApp: Application() {

    override fun onCreate() {
        super.onCreate()

        // Load osmdroid config
        Configuration.getInstance().load(this, getSharedPreferences("osmdroid", MODE_PRIVATE))

        startKoin{
            androidContext(this@myApp)
            modules(gpxModule)
        }
    }
}