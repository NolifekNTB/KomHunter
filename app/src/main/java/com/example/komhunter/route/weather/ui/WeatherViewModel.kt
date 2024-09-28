package com.example.komhunter.route.weather.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.komhunter.route.uploadGPX.data.models.GpxCoordinate
import com.example.komhunter.route.uploadGPX.data.repositories.GpxRepository
import com.example.komhunter.route.weather.data.models.WeatherData
import com.example.komhunter.route.weather.data.repositories.ForecastRepository
import com.example.komhunter.route.weather.data.repositories.WeatherRepository
import com.example.komhunter.route.weather.domain.mappers.convertApiToDb
import com.example.komhunter.route.weather.domain.usecases.aggregateWindImpact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeatherViewModel(
    private val dbWeatherRepository: WeatherRepository,
    private val apiWeatherRepository: ForecastRepository,
    private val gpxRepository: GpxRepository,
) : ViewModel() {
    private val _bestTime = MutableStateFlow<Pair<Long, Double>?>(null)
    val bestTime: StateFlow<Pair<Long, Double>?> = _bestTime

    private val _gpxCoordinates = MutableStateFlow<List<GpxCoordinate>>(emptyList())
    val gpxCoordinates: StateFlow<List<GpxCoordinate>> = _gpxCoordinates

    private val _weatherData = MutableStateFlow<List<WeatherData>>(emptyList())
    val weatherData: StateFlow<List<WeatherData>> = _weatherData

    init {
        fetchGpxCoordinates()
    }

    fun fetchGpxCoordinates() {
        viewModelScope.launch {
            _gpxCoordinates.value = gpxRepository.getAllCoordinates().first()
            _gpxCoordinates.value.firstOrNull()?.let { coordinate ->
                fetchWeatherDataApi(coordinate.latitude, coordinate.longitude)
            }
        }
    }

    private suspend fun fetchWeatherDataApi(
        latitude: Double,
        longitude: Double,
    ) {
        val weatherFromApi = apiWeatherRepository.getWeatherApi(latitude, longitude)
        insertWeatherData(convertApiToDb(weatherFromApi))
    }

    private suspend fun insertWeatherData(data: List<WeatherData>) {
        dbWeatherRepository.insertAll(data)
        fetchWeatherDataFromDb()
    }

    private suspend fun fetchWeatherDataFromDb() {
        _weatherData.value = dbWeatherRepository.getAllWeatherData()
        if (_gpxCoordinates.value.isNotEmpty() && _weatherData.value.isNotEmpty()) {
            calculateBestTime()
        }
    }

    private suspend fun calculateBestTime() {
        val bestTime =
            withContext(Dispatchers.Default) {
                _weatherData.value
                    .map { weatherData ->
                        weatherData.dt to aggregateWindImpact(_gpxCoordinates.value, weatherData)
                    }.maxByOrNull { it.second }
            }
        _bestTime.value = bestTime
    }

    fun deleteAllGpx() {
        viewModelScope.launch {
            gpxRepository.deleteAll()
            _gpxCoordinates.value = emptyList()
        }
    }
}
