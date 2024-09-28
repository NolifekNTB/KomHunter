package com.example.komhunter.core.data.local

import com.example.komhunter.route.weather.data.models.WeatherData
import junit.framework.TestCase.assertEquals
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.Test

class WeatherDataTest {
    @Test
    fun testDefaultValues() {
        // Given
        val weatherData = WeatherData()

        // Then
        assertEquals(0, weatherData.dt)
        assertEquals(0.0, weatherData.temp)
        assertEquals(0.0, weatherData.feels_like)
    }

    @Test
    fun testSerialization() {
        // Given
        val weatherData =
            WeatherData(
                dt = 1625247600,
                temp = 25.0,
                weather_main = "Clear",
                weather_description = "clear sky",
            )

        // When
        val jsonString = Json.encodeToString(weatherData)
        val deserializedWeatherData = Json.decodeFromString<WeatherData>(jsonString)

        // Then
        assertEquals(weatherData, deserializedWeatherData)
    }
}
