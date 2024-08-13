package com.example.komhunter.Core.di

import android.app.Application
import com.example.komhunter.Route.Weather.di.weatherModule
import com.example.komhunter.Route.Maps.di.mapsModule
import com.example.komhunter.Route.UploadGPX.di.gpxModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.osmdroid.config.Configuration

class AppModule: Application() {

    override fun onCreate() {
        super.onCreate()
        initializeOsmdroid()
        initializeDependencyInjection()
    }

    private fun initializeOsmdroid(){
        Configuration.getInstance().load(this, getSharedPreferences("osmdroid", MODE_PRIVATE))
    }

    private fun initializeDependencyInjection() {
        startKoin{
            androidContext(this@AppModule)
            modules (
                gpxModule,
                mapsModule,
                weatherModule
            )
        }
    }
}