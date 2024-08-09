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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun WeatherScreen() {
    val viewModel: WeatherViewModel = koinViewModel()
    val bestTime by viewModel.bestTime.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if(bestTime == null) {
            Text(text = "Loading...", textAlign = TextAlign.Center)
        } else {
            bestTime?.let { (timestamp, impact) ->
                val date = Date(timestamp * 1000)
                val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                Text(text = "Best time for KOM: ${sdf.format(date)}\nWind Impact: ${impact.toInt()} km/h", textAlign = TextAlign.Center)
            }
        }
    }
}