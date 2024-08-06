package com.example.komhunter.Weather.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.komhunter.Weather.data.WeatherRepository
import com.example.komhunter.core.data.network.WeatherForecast
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class WeatherViewModel(private val repository: WeatherRepository) : ViewModel() {
    private val _weatherData = MutableStateFlow<WeatherForecast?>(null)
    val weatherData: StateFlow<WeatherForecast?> = _weatherData.asStateFlow()

    val latitude = 51.7372
    val longitude = 19.6423

    fun getWeather() {
        viewModelScope.launch {
            val data = repository.getWeather(latitude, longitude)
            _weatherData.value = data
        }
    }
}