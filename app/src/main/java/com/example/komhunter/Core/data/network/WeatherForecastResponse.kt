package com.example.komhunter.Core.data.network

import com.example.komhunter.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url

class WeatherForecastResponse(
    private val client: HttpClient
) {
    suspend fun getWeatherForecast(latitude: String, longitude: String): WeatherForecast {
        return client.get {
            url("https://api.openweathermap.org/data/2.5/forecast")
            parameter("lat", latitude)
            parameter("lon", longitude)
            parameter("appid", BuildConfig.API_KEY)
            parameter("units", "metric")
            parameter("cnt", "40")
        }.body()
    }
}