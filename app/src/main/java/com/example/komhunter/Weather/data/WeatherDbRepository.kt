package com.example.komhunter.Weather.data

import com.example.komhunter.core.data.database.daos.WeatherDao
import com.example.komhunter.core.data.database.entities.WeatherDataEntity
import com.example.komhunter.core.data.network.ResponseService
import com.example.komhunter.core.data.network.WeatherForecast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherDbRepository(
    private val weatherDao: WeatherDao
): WeatherDao {

    override suspend fun insertAll(weatherData: List<WeatherDataEntity>) {
        withContext(Dispatchers.IO) {
            weatherDao.insertAll(weatherData)
        }
    }

    override suspend fun getStoredWeatherData(): List<WeatherDataEntity> {
        return withContext(Dispatchers.IO) {
            weatherDao.getStoredWeatherData()
        }
    }
}