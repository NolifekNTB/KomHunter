package com.example.komhunter.Route.Weather.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "weather_data")
@Serializable
data class WeatherData(
    @PrimaryKey val dt: Long,
    val temp: Double,
    val feels_like: Double,
    val temp_min: Double,
    val temp_max: Double,
    val pressure: Int,
    val humidity: Int,
    val weather_main: String,
    val weather_description: String,
    val weather_icon: String,
    val clouds_all: Int,
    val wind_speed: Double,
    val wind_deg: Int,
    val wind_gust: Double,
    val visibility: Int,
    val pop: Float,
    val sys_pod: String,
    val dt_txt: String
)