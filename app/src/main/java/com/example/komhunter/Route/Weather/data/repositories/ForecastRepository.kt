package com.example.komhunter.Route.Weather.data.repositories

import com.example.komhunter.Core.data.network.WeatherForecastResponse
import com.example.komhunter.Core.data.network.WeatherForecast

class ForecastRepository(
    private val weatherForecast: WeatherForecastResponse
) {
    suspend fun getWeatherApi(latitude: Double, longitude: Double): WeatherForecast {
        return weatherForecast.getWeatherForecast(latitude.toString(), longitude.toString())
    }
}