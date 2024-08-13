package com.example.komhunter.Route.Weather.data.repositories

import android.content.Context
import com.example.komhunter.Core.data.local.WeatherDatabase
import com.example.komhunter.Core.data.local.daos.WeatherDao
import com.example.komhunter.Route.Weather.data.models.WeatherData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRepository(context: Context): WeatherDao {
    val weatherDao = WeatherDatabase.getDatabase(context).weatherDao()

    override suspend fun insertAll(weatherData: List<WeatherData>) {
        withContext(Dispatchers.IO) {
            weatherDao.insertAll(weatherData)
        }
    }

    override suspend fun getAllWeatherData(): List<WeatherData> {
        return withContext(Dispatchers.IO) {
            weatherDao.getAllWeatherData()
        }
    }
}