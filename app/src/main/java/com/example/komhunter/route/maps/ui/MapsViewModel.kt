package com.example.komhunter.route.maps.ui

import android.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.komhunter.route.uploadGPX.data.models.GpxCoordinate
import com.example.komhunter.route.uploadGPX.data.repositories.GpxRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.osmdroid.util.BoundingBox
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Polyline

class MapsViewModel(
    private val repository: GpxRepository,
) : ViewModel() {
    private var _gpxCoordinates = MutableStateFlow<List<GpxCoordinate>>(emptyList())
    var gpxCoordinates = _gpxCoordinates.asStateFlow()

    private val _boundingBox = MutableStateFlow<BoundingBox?>(null)
    val boundingBox = _boundingBox.asStateFlow()

    init {
        loadAllCoordinates()
    }

    private fun loadAllCoordinates() {
        viewModelScope.launch {
            _gpxCoordinates.value = repository.getAllCoordinates().first()
            calculateBoundingBox()
        }
    }

    private fun calculateBoundingBox() {
        val coordinates = _gpxCoordinates.value
        if (coordinates.isNotEmpty()) {
            val latitudes = coordinates.map { it.latitude }
            val longitudes = coordinates.map { it.longitude }
            _boundingBox.value =
                BoundingBox(
                    latitudes.maxOrNull() ?: 0.0,
                    longitudes.maxOrNull() ?: 0.0,
                    latitudes.minOrNull() ?: 0.0,
                    longitudes.minOrNull() ?: 0.0,
                )
        }
    }

    fun createPolyline(): Polyline? {
        val coordinates = _gpxCoordinates.value
        return if (coordinates.isNotEmpty()) {
            val points = coordinates.map { GeoPoint(it.latitude, it.longitude) }
            Polyline().apply {
                setPoints(points)
                color = Color.BLUE
                width = 10f
            }
        } else {
            null
        }
    }
}
