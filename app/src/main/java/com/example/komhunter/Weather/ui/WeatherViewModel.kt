package com.example.komhunter.Weather.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.komhunter.Weather.data.WeatherRepository
import com.example.komhunter.Weather.model.aggregateWindImpact
import com.example.komhunter.core.data.database.entities.GpxCoordinate
import com.example.komhunter.core.data.database.entities.WeatherDataEntity
import com.example.komhunter.uploadGPX.data.GpxRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class WeatherViewModel(
    private val weatherRepository: WeatherRepository,
    private val gpxRepository: GpxRepository
) : ViewModel() {
    private val _windImpact = MutableStateFlow<Double?>(null)
    val windImpact: StateFlow<Double?> = _windImpact

    private val _gpxCoordinates = MutableStateFlow<List<GpxCoordinate>>(emptyList())
    val gpxCoordinates: StateFlow<List<GpxCoordinate>> = _gpxCoordinates

    private val _weatherData = MutableStateFlow<List<WeatherDataEntity>>(emptyList())
    val weatherData: StateFlow<List<WeatherDataEntity>> = _weatherData

    init {
        fetchGpxCoordinates()
    }

    private fun fetchGpxCoordinates() {
        viewModelScope.launch {
            _gpxCoordinates.value = gpxRepository.getAllCoordinates().first()
            if (_gpxCoordinates.value.isNotEmpty()) {
                fetchWeatherData(
                    _gpxCoordinates.value.first().latitude,
                    _gpxCoordinates.value.first().longitude
                )
            }
        }
    }

    fun fetchWeatherData(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            weatherRepository.getWeather(latitude, longitude)
            getWeatherDataFromDb()
        }
    }

    private fun getWeatherDataFromDb() {
        viewModelScope.launch {
            val data = weatherRepository.getStoredWeatherData()
            _weatherData.value = data
            if (_gpxCoordinates.value.isNotEmpty() && _weatherData.value.isNotEmpty()) {
                calculateWindImpact()
            }
        }
    }

    private fun calculateWindImpact() {
        viewModelScope.launch {
            val impact = withContext(Dispatchers.Default) {
                aggregateWindImpact(_gpxCoordinates.value, _weatherData.value)
            }
            _windImpact.value = impact
        }
    }
}







