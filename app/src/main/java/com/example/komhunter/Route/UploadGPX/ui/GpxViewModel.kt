package com.example.komhunter.Route.UploadGPX.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.komhunter.Route.UploadGPX.data.models.GpxCoordinate
import com.example.komhunter.Route.UploadGPX.data.repositories.GpxRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class GpxViewModel(
    private val repository: GpxRepository
): ViewModel() {
    private var _gpxCoordinates = MutableStateFlow<List<GpxCoordinate>>(emptyList())
    var gpxCoordinates = _gpxCoordinates.asStateFlow()

    fun insertAll(coordinates: List<GpxCoordinate>, onComplete: () -> Unit) {
        viewModelScope.launch {
            repository.insertAll(coordinates)
            loadAllCoordinates()
            onComplete()
        }
    }

    fun deleteAll() {
        viewModelScope.launch {
            repository.deleteAll()
            loadAllCoordinates()
        }
    }

    private fun loadAllCoordinates() {
        viewModelScope.launch {
            _gpxCoordinates.value = repository.getAllCoordinates().first()
        }
    }

    private fun clearCoordinates() {
        _gpxCoordinates.value = emptyList()
    }
 }