package com.example.komhunter.Weather.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel

@Composable
fun WeatherScreen(viewModel: WeatherViewModel = koinViewModel()) {
    val windImpact by viewModel.windImpact.collectAsState()
    val gpxCoordinates by viewModel.gpxCoordinates.collectAsState()
    val weatherData by viewModel.weatherData.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if(windImpact == null) {
            Text(text = "Loading...", textAlign = TextAlign.Center)
        } else {
            windImpact?.let { impact ->
                Text(text = "Total Wind Impact: ${impact.toInt()} km/h", textAlign = TextAlign.Center)
            }
        }
    }
}