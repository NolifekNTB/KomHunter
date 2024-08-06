package com.example.komhunter.uploadGPX.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.komhunter.core.database.GpxCoordinate
import com.example.komhunter.uploadGPX.data.GpxRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class GpxViewModel(private val repository: GpxRepository): ViewModel() {
    private var _gpxCoordinates = MutableStateFlow<List<GpxCoordinate>>(emptyList())
    var gpxCoordinates = _gpxCoordinates.asStateFlow()

    init {
        getAllCoordinates()
    }

    fun insertAll(coordinates: List<GpxCoordinate>) {
        viewModelScope.launch {
            repository.insertAll(coordinates)
        }
    }

    fun getAllCoordinates() {
        viewModelScope.launch {
            _gpxCoordinates.value = repository.getAllCoordinates().first()
        }
    }
 }