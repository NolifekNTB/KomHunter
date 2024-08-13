package com.example.komhunter.Core.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.komhunter.Home.HomeScreen
import com.example.komhunter.Profile.ProfileScreen
import com.example.komhunter.Route.Weather.ui.WeatherScreen
import com.example.komhunter.Route.Maps.ui.MapScreen
import com.example.komhunter.Route.UploadGPX.ui.GpxFilePicker
import com.example.komhunter.Core.ui.navigation.models.HomeScreen
import com.example.komhunter.Core.ui.navigation.models.MapScreen
import com.example.komhunter.Core.ui.navigation.models.ProfileScreen
import com.example.komhunter.Core.ui.navigation.models.RouteScreen
import com.example.komhunter.Core.ui.navigation.models.WeatherScreen

@Composable
fun NavigationHost(navController: NavHostController, padding: PaddingValues) {
    NavHost(
        navController = navController,
        startDestination = HomeScreen,
        modifier = Modifier.padding(padding)
    ) {
        composable<HomeScreen> {
            HomeScreen()
        }

        composable<ProfileScreen> {
            ProfileScreen()
        }

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
