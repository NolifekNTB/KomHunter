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
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = ScreenA) {
        composable<ScreenA> {
            GpxFilePicker() {
                navController.navigate(ScreenB)
            }
        }
        composable<ScreenB> {
            MapScreen() {
                navController.navigate(ScreenC)
            }
        }
        composable<ScreenC> {
            WeatherScreen()
        }
    }
}

@Serializable
object ScreenA

@Serializable
object ScreenB

@Serializable
object ScreenC
