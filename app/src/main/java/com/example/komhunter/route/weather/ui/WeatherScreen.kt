package com.example.komhunter.route.weather.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
fun WeatherScreen(viewModel: WeatherViewModel = koinViewModel()) {
    val bestTime by viewModel.bestTime.collectAsState()

    WeatherScreenContent(bestTime)
}

@Composable
fun WeatherScreenContent(bestTime: Pair<Long, Double>?) {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        when (bestTime) {
            null -> LoadingContent()
            else -> BestTimeContent(bestTime)
        }
    }
}

@Composable
private fun LoadingContent() {
    Text(text = "Loading...", textAlign = TextAlign.Center)
}

@Composable
private fun BestTimeContent(bestTime: Pair<Long, Double>) {
    val date = Date(bestTime.first * 1000)
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

    Text(
        text = "Best time for KOM: ${sdf.format(date)}\nWind Impact: ${bestTime.second.toInt()} km/h",
        textAlign = TextAlign.Center,
    )
}
