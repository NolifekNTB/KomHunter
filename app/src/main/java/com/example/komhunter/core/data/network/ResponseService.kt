package com.example.komhunter.core.data.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url

class ResponseService(
    private val client: HttpClient
) {
    suspend fun getResponse(latitude: String, longitude: String): WeatherForecast {
        return client.get {
            url("https://api.openweathermap.org/data/2.5/forecast")
            parameter("lat", latitude)
            parameter("lon", longitude)
            parameter("appid", "14efda8337fb20cda4a865dbee112ae8")
            parameter("units", "metric")
            parameter("cnt", "5")
        }.body()
    }
}