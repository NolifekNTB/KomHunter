package com.example.komhunter.core.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.komhunter.Weather.ui.WeatherScreen
import com.example.komhunter.maps.ui.MapScreen
import com.example.komhunter.uploadGPX.ui.GpxFilePicker
import kotlinx.serialization.Serializable

@Composable
fun NavigationHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = RouteScreen) {
        composable<RouteScreen> {
            GpxFilePicker() {
                navController.navigate(MapScreen)
            }
        }
        composable<MapScreen> {
            MapScreen() {
                navController.navigate(WeatherScreen)
            }
        }
        composable<WeatherScreen> {
            WeatherScreen()
        }
    }
}

@Serializable
object RouteScreen

@Serializable
object MapScreen

@Serializable
object WeatherScreen
