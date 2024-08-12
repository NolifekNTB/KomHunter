package com.example.komhunter.maps.ui

import android.content.Context
import android.graphics.Color
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.komhunter.core.ui.theme.white
import org.koin.androidx.compose.koinViewModel
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.BoundingBox
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Polyline

@Composable
fun MapScreen(onNavigate: () -> Unit) {
    val context = LocalContext.current
    val viewModel = koinViewModel<MapsViewModel>()
    val gpxCoordinates = viewModel.gpxCoordinates.collectAsState().value
    val mapView = rememberMapViewWithLifecycle(context)



    LaunchedEffect(gpxCoordinates) {
        if (gpxCoordinates.isNotEmpty()) {
            val points = gpxCoordinates.map { GeoPoint(it.latitude, it.longitude) } //convert all gpx coordinates to GeoPoints
            val polyline = Polyline().apply {  //create a polyline
                setPoints(points)
                color = Color.BLUE
                width = 10f
            }
            mapView.overlays.clear()
            mapView.overlays.add(polyline)

            val latitudes = gpxCoordinates.map { it.latitude }
            val longitudes = gpxCoordinates.map { it.longitude }
            val boundingBox = BoundingBox(
                latitudes.maxOrNull() ?: 0.0,
                longitudes.maxOrNull() ?: 0.0,
                latitudes.minOrNull() ?: 0.0,
                longitudes.minOrNull() ?: 0.0
            )

            // Set zoom to fit the bounding box
            mapView.zoomToBoundingBox(boundingBox, true)

            mapView.invalidate()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { mapView }
        )
        FloatingActionButton(
            onClick = { onNavigate() },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 100.dp, end = 20.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.LocationOn,
                contentDescription = "Show Weather Data",
                tint = white
            )
        }
    }
}

@Composable
fun rememberMapViewWithLifecycle(context: Context): MapView {
    val mapView = remember {
        MapView(context).apply {
            setTileSource(TileSourceFactory.MAPNIK)
            setMultiTouchControls(true)
        }
    }

    DisposableEffect(mapView) {
        onDispose {
            mapView.onDetach()
        }
    }

    return mapView
}
