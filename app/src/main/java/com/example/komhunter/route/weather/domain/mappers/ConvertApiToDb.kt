package com.example.komhunter.route.weather.domain.mappers

import com.example.komhunter.core.data.network.WeatherForecast
import com.example.komhunter.route.weather.data.models.WeatherData

fun convertApiToDb(response: WeatherForecast): List<WeatherData> {
    val weatherEntities =
        response.list.map { weather ->
            WeatherData(
                dt = weather.dt,
                temp = weather.main.temp,
                feels_like = weather.main.feels_like,
                temp_min = weather.main.temp_min,
                temp_max = weather.main.temp_max,
                pressure = weather.main.pressure,
                humidity = weather.main.humidity,
                weather_main = weather.weather.firstOrNull()?.main ?: "",
                weather_description = weather.weather.firstOrNull()?.description ?: "",
                weather_icon = weather.weather.firstOrNull()?.icon ?: "",
                clouds_all = weather.clouds.all,
                wind_speed = weather.wind.speed,
                wind_deg = weather.wind.deg,
                wind_gust = weather.wind.gust,
                visibility = weather.visibility,
                pop = weather.pop,
                sys_pod = weather.sys.pod,
                dt_txt = weather.dt_txt,
            )
        }
    return weatherEntities
}
