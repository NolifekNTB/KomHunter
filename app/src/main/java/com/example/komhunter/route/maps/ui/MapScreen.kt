package com.example.komhunter.route.maps.ui

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.komhunter.core.ui.theme.white
import org.koin.androidx.compose.koinViewModel
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.BoundingBox
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Polyline

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MapScreen(
    viewModel: MapsViewModel = koinViewModel(),
    onNavigate: () -> Unit,
) {
    val context = LocalContext.current
    val boundingBox by viewModel.boundingBox.collectAsState()
    val mapView = rememberMapViewWithLifecycle(context)

    DisplayMapContent(mapView, viewModel.createPolyline(), boundingBox)

    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .semantics { testTagsAsResourceId = true },
    ) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { mapView },
        )
        NavigationFab(onNavigate, Modifier.align(Alignment.BottomEnd))
    }
}

@Composable
fun DisplayMapContent(
    mapView: MapView,
    polyline: Polyline?,
    boundingBox: BoundingBox?,
) {
    LaunchedEffect(polyline, boundingBox) {
        if (polyline != null && boundingBox != null) {
            mapView.overlays.clear()
            mapView.overlays.add(polyline)
            mapView.zoomToBoundingBox(boundingBox, true)
            mapView.invalidate()
        }
    }
}

@Composable
fun NavigationFab(
    onNavigate: () -> Unit,
    modifier: Modifier,
) {
    FloatingActionButton(
        onClick = { onNavigate() },
        modifier =
            modifier
                .padding(bottom = 100.dp, end = 20.dp)
                .testTag("WeatherDataFab"),
    ) {
        Icon(
            imageVector = Icons.Filled.LocationOn,
            contentDescription = "Show Weather Data",
            tint = white,
        )
    }
}

@Composable
fun rememberMapViewWithLifecycle(context: Context): MapView {
    val mapView =
        remember {
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
