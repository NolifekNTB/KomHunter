package com.example.komhunter.core.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.example.komhunter.core.database.GpxCoordinate
import com.example.komhunter.core.database.GpxDatabase
import com.example.komhunter.uploadGPX.ui.GpxFilePicker
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.views.MapView
import android.graphics.Color
import android.util.Log
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        Configuration.getInstance().load(this, getSharedPreferences("osmdroid", MODE_PRIVATE))

        setContent {
            Column(Modifier.fillMaxSize()) {
                GpxFileUpload(Modifier.weight(0.13f))
                MapApp(Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun MapApp(modifier: Modifier) {
    val context = LocalContext.current
    val db = GpxDatabase.getDatabase(context)

    val gpxCoordinates by produceState<List<GpxCoordinate>>(initialValue = emptyList(), producer = {
        value = db.gpxCoordinateDao().getAllCoordinates()
    })

    MapScreen(modifier, gpxCoordinates)
}

@Composable
fun MapScreen(modifier: Modifier, gpxCoordinates: List<GpxCoordinate>) {
    val context = LocalContext.current

    val mapView = remember {
        MapView(context).apply {
            setTileSource(TileSourceFactory.MAPNIK)
            setMultiTouchControls(true)
            controller.setZoom(15)
            if(gpxCoordinates.isNotEmpty()) {
                controller.setCenter(
                    GeoPoint(
                        gpxCoordinates[0].latitude,
                        gpxCoordinates[1].longitude
                    )
                )
            }
        }
    }

    LaunchedEffect(gpxCoordinates) {
        val points = gpxCoordinates.map { GeoPoint(it.latitude, it.longitude) }
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
        modifier = modifier,
        factory = { mapView }
    )
}

@Composable
fun GpxFileUpload(modifier: Modifier) {
    val context = LocalContext.current
    val coordinatesState = remember { mutableStateOf<List<GpxCoordinate>>(emptyList()) }

    LaunchedEffect(Unit) {
        val db = GpxDatabase.getDatabase(context)
        coordinatesState.value = db.gpxCoordinateDao().getAllCoordinates()
    }

    Column(modifier) {
        GpxFilePicker { coordinates ->
            coordinatesState.value = coordinates
        }
    }
}

