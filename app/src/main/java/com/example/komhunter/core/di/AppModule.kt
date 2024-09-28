package com.example.komhunter.core.di

import android.app.Application
import com.example.komhunter.route.maps.di.MapsModule
import com.example.komhunter.route.uploadGPX.di.GpxModule
import com.example.komhunter.route.weather.di.weatherModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.osmdroid.config.Configuration

class AppModule : Application() {
    override fun onCreate() {
        super.onCreate()
        initializeOsmdroid()
        initializeDependencyInjection()
    }

    private fun initializeOsmdroid() {
        Configuration.getInstance().load(this, getSharedPreferences("osmdroid", MODE_PRIVATE))
    }

    private fun initializeDependencyInjection() {
        startKoin {
            androidContext(this@AppModule)
            modules(
                GpxModule,
                MapsModule,
                weatherModule,
            )
        }
    }
}
