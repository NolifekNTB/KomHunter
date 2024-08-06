package com.example.komhunter.Weather.data

import com.example.komhunter.core.data.network.ResponseService
import com.example.komhunter.core.data.network.WeatherForecast

class WeatherRepository(
    private val responseService: ResponseService
) {
    suspend fun getWeather(latitude: Double, longitude: Double): WeatherForecast {
        return responseService.getResponse(latitude.toString(), longitude.toString())
    }
}