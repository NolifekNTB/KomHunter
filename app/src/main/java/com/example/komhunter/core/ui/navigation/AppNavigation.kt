package com.example.komhunter.core.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.komhunter.Weather.ui.WeatherScreen
import com.example.komhunter.maps.ui.MapScreen
import com.example.komhunter.uploadGPX.ui.GpxFilePicker
import com.example.komhunter.uploadGPX.ui.GpxViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.GpxPicker.route) {
        composable(Screen.GpxPicker.route) { entry ->
            val viewModel = entry.sharedViewModel<GpxViewModel>(navController, Screen.GpxPicker.route)

            GpxFilePicker() {
                navController.navigate(Screen.MapView.route)
            }
        }
        composable(Screen.MapView.route) { entry ->
            val viewModel = entry.sharedViewModel<GpxViewModel>(navController, Screen.MapView.route)

            MapScreen() {
                navController.navigate(Screen.WeatherScreen.route)
            }
        }
        composable(Screen.WeatherScreen.route) {
            WeatherScreen()
        }
    }
}



@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(
    navController: NavHostController,
    navGraphRoute: String
): T {
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return parentEntry.getKoinViewModel()
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.getKoinViewModel(): T {
    return getViewModel(viewModelStoreOwner = this)
}
