package com.example.komhunter.Weather.ui

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
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel

@Composable
fun WeatherScreen(viewModel: WeatherViewModel = koinViewModel()) {
    val weatherData by viewModel.weatherData.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getWeather()
    }

    weatherData?.let { data ->
        Column(modifier = Modifier.padding(16.dp)) {
            data.list.forEach { weather ->
                Text("" +
                        "Date: ${weather.dt_txt}, " +
                        "Wind Speed: ${weather.wind.speed}, " +
                        "Wind Direction: ${weather.wind.deg}",
                    modifier = Modifier.padding(vertical = 4.dp))
                 }
            }
    } ?: run {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Loading weather data...")
        }
    }
}