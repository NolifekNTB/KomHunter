package com.example.komhunter.Weather.data

import com.example.komhunter.core.data.database.daos.WeatherDao
import com.example.komhunter.core.data.database.entities.WeatherDataEntity
import com.example.komhunter.core.data.network.ResponseService
import com.example.komhunter.core.data.network.WeatherForecast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRepository(
    private val responseService: ResponseService,
    private val weatherDao: WeatherDao
) {
    suspend fun getWeather(latitude: Double, longitude: Double): List<WeatherDataEntity> {
        val response = responseService.getResponse(latitude.toString(), longitude.toString())
        val weatherEntities = response.list.map { weather ->
            WeatherDataEntity(
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
                dt_txt = weather.dt_txt
            )
        }
        withContext(Dispatchers.IO) {
            weatherDao.insertAll(weatherEntities)
        }
        return weatherEntities
    }

    suspend fun getStoredWeatherData(): List<WeatherDataEntity> {
        return withContext(Dispatchers.IO) {
            weatherDao.getAllWeatherData()
        }
    }
}