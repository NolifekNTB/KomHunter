package com.example.komhunter.Weather.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.komhunter.Weather.data.WeatherRepository
import com.example.komhunter.core.data.database.daos.WeatherDao
import com.example.komhunter.core.data.database.entities.WeatherDataEntity
import com.example.komhunter.core.data.network.WeatherForecast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class WeatherViewModel(
    private val repository: WeatherRepository
) : ViewModel() {
    private val _weatherData = MutableStateFlow<List<WeatherDataEntity>?>(null)
    val weatherData: StateFlow<List<WeatherDataEntity>?> = _weatherData.asStateFlow()

    val latitude = 51.7372
    val longitude = 19.6423

    fun getWeather() {
        viewModelScope.launch {
            val data = repository.getWeather(latitude, longitude)
            _weatherData.value = data
        }
    }

    fun getStoredWeather() {
        viewModelScope.launch {
            val data = repository.getStoredWeatherData()
            _weatherData.value = data
        }
    }
}







