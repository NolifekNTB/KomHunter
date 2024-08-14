package com.example.komhunter.Route.Weather.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "weather_data")
@Serializable
data class WeatherData(
    @PrimaryKey val dt: Long = 0,
    val temp: Double = 0.0,
    val feels_like: Double = 0.0,
    val temp_min: Double = 0.0,
    val temp_max: Double = 0.0,
    val pressure: Int = 0,
    val humidity: Int = 0,
    val weather_main: String = "",
    val weather_description: String = "",
    val weather_icon: String = "",
    val clouds_all: Int = 0,
    val wind_speed: Double = 0.0,
    val wind_deg: Int = 0,
    val wind_gust: Double = 0.0,
    val visibility: Int = 0,
    val pop: Float = 0.0f,
    val sys_pod: String = "",
    val dt_txt: String = ""
)