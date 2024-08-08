package com.example.komhunter.maps.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.komhunter.core.data.database.entities.GpxCoordinate
import com.example.komhunter.uploadGPX.data.GpxRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MapsViewModel(private val repository: GpxRepository): ViewModel() {
    private var _gpxCoordinates = MutableStateFlow<List<GpxCoordinate>>(emptyList())
    var gpxCoordinates = _gpxCoordinates.asStateFlow()

    init {
        getAllCoordinates()
    }

    fun getAllCoordinates() {
        viewModelScope.launch {
            _gpxCoordinates.value = repository.getAllCoordinates().first()
        }
    }
}