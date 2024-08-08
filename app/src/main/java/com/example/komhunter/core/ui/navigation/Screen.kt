package com.example.komhunter.core.ui.navigation

sealed class Screen(val route: String) {
    data object GpxPicker : Screen("gpxPicker")
    data object MapView : Screen("mapView")
    data object WeatherScreen : Screen("weatherScreen")
}