package com.example.komhunter.maps.ui

import android.graphics.Color
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.example.komhunter.core.database.GpxCoordinate
import com.example.komhunter.core.database.GpxDatabase
import com.example.komhunter.uploadGPX.ui.GpxViewModel
import kotlinx.coroutines.launch
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Polyline

@Composable
fun MapScreen(viewModel: GpxViewModel) {
    val context = LocalContext.current
    val gpxCoordinatesDb = viewModel.gpxCoordinates.collectAsState().value
    val gpxCoordinates = remember { mutableStateOf<List<GpxCoordinate>>(emptyList()) }

    LaunchedEffect(Unit) {
        launch {
            gpxCoordinates.value = gpxCoordinatesDb
        }
    }

    val mapView = remember {
        MapView(context).apply {
            setTileSource(TileSourceFactory.MAPNIK)
            setMultiTouchControls(true)
            controller.setZoom(15)
            if(gpxCoordinates.value.isNotEmpty()) {
                Log.d("testowanie", "MapScreen: ${gpxCoordinates.value[0].latitude}")
                Log.d("testowanie", "MapScreen: ${gpxCoordinates.value[0].longitude}")
                controller.setCenter(
                    GeoPoint(
                        gpxCoordinates.value[0].latitude,
                        gpxCoordinates.value[1].longitude
                    )
                )
            }
        }
    }

    LaunchedEffect(gpxCoordinates.value) {
        val points = gpxCoordinates.value.map { GeoPoint(it.latitude, it.longitude) }
        val polyline = Polyline().apply {
            setPoints(points)
            color = Color.BLUE
            width = 5f
        }
        mapView.overlays.clear()
        mapView.overlays.add(polyline)
        mapView.invalidate()
    }

    DisposableEffect(mapView) {
        onDispose {
            mapView.onDetach()
        }
    }

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { mapView }
    )
}
