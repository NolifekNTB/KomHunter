package com.example.komhunter.route.weather.data.repositories

import android.content.Context
import com.example.komhunter.core.data.local.WeatherDatabase
import com.example.komhunter.core.data.local.daos.WeatherDao
import com.example.komhunter.route.weather.data.models.WeatherData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRepository(
    context: Context,
) : WeatherDao {
    var weatherDao = WeatherDatabase.getDatabase(context).weatherDao()

    override suspend fun insertAll(weatherData: List<WeatherData>) {
        withContext(Dispatchers.IO) {
            weatherDao.insertAll(weatherData)
        }
    }

    override suspend fun getAllWeatherData(): List<WeatherData> =
        withContext(Dispatchers.IO) {
            weatherDao.getAllWeatherData()
        }
}
