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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class WeatherViewModel(
    private val weatherRepository: WeatherRepository,
    private val gpxRepository: GpxRepository
) : ViewModel() {
    private val _bestTime = MutableStateFlow<Pair<Long, Double>?>(null)
    val bestTime: StateFlow<Pair<Long, Double>?> = _bestTime

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

    private fun fetchWeatherData(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            weatherRepository.getWeather(latitude, longitude)
            fetchWeatherDataFromDb()
        }
    }

    private fun fetchWeatherDataFromDb() {
        viewModelScope.launch {
            val data = weatherRepository.getStoredWeatherData()
            _weatherData.value = data
            if (_gpxCoordinates.value.isNotEmpty() && _weatherData.value.isNotEmpty()) {
                calculateBestTime()
            }
        }
    }

    private fun calculateBestTime() {
        viewModelScope.launch {
            val bestTime = withContext(Dispatchers.Default) {
                _weatherData.value
                    .map { weatherData ->
                        weatherData.dt to aggregateWindImpact(_gpxCoordinates.value, weatherData)
                    }
                    .maxByOrNull { it.second }
            }
            _bestTime.value = bestTime
        }
    }
}







