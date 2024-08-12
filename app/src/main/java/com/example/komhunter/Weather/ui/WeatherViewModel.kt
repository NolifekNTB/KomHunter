package com.example.komhunter.Weather.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.komhunter.Weather.data.WeatherApiRepository
import com.example.komhunter.Weather.data.WeatherDbRepository
import com.example.komhunter.Weather.model.ConvertApiToDb
import com.example.komhunter.Weather.model.WindCalculations.aggregateWindImpact
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
    private val DbWeatherRepository: WeatherDbRepository,
    private val gpxRepository: GpxRepository,
    private val ApiWeatherRepository: WeatherApiRepository
) : ViewModel() {
    private val _bestTime = MutableStateFlow<Pair<Long, Double>?>(null)
    val bestTime: StateFlow<Pair<Long, Double>?> = _bestTime

    private val _gpxCoordinates = MutableStateFlow<List<GpxCoordinate>>(emptyList())
    val gpxCoordinates: StateFlow<List<GpxCoordinate>> = _gpxCoordinates

    private val _weatherData = MutableStateFlow<List<WeatherDataEntity>>(emptyList())
    val weatherData: StateFlow<List<WeatherDataEntity>> = _weatherData

    fun fetchGpxCoordinates() {
        viewModelScope.launch {
            _gpxCoordinates.value = gpxRepository.getAllCoordinates().first()
            if (_gpxCoordinates.value.isNotEmpty()) {
                fetchWeatherDataApi(
                    _gpxCoordinates.value.first().latitude,
                    _gpxCoordinates.value.first().longitude
                )
            }
        }
    }

    private fun fetchWeatherDataApi(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            val weatherFromApi = ApiWeatherRepository.getWeatherApi(latitude, longitude)
            insertWeatherData(ConvertApiToDb(weatherFromApi))
        }
    }

    private fun insertWeatherData(data: List<WeatherDataEntity>) {
        viewModelScope.launch {
            DbWeatherRepository.insertAll(data)
            fetchWeatherDataFromDb()
        }
    }

    private fun fetchWeatherDataFromDb() {
        viewModelScope.launch {
            val data = DbWeatherRepository.getStoredWeatherData()
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

    private fun deleteALlGpx (){
        viewModelScope.launch {
            gpxRepository.deleteAll()
        }
    }
}







