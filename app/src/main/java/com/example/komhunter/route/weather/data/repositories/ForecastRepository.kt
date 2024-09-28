package com.example.komhunter.route.weather.data.repositories

import com.example.komhunter.core.data.network.WeatherForecast
import com.example.komhunter.core.data.network.WeatherForecastResponse

class ForecastRepository(
    private val weatherForecast: WeatherForecastResponse,
) {
    suspend fun getWeatherApi(
        latitude: Double,
        longitude: Double,
    ): WeatherForecast = weatherForecast.getWeatherForecast(latitude.toString(), longitude.toString())
}
