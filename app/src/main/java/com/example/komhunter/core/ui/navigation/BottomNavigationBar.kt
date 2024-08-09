package com.example.komhunter.core.ui.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.example.komhunter.R

@Composable
fun BottomNavigationBar(navController: NavHostController, selectedItemIndex: Int, onItemSelected: (Int) -> Unit) {
    val items = listOf(
        BottomNavigationItem(
            screen = RouteScreen,
            title = "Home",
            icon = R.drawable.home
        ),
        BottomNavigationItem(
            screen = MapScreen,
            title = "Route",
            icon = R.drawable.route
        ),
        BottomNavigationItem(
            screen = WeatherScreen,
            title = "Weather",
            icon = R.drawable.cloud
        ),
    )

    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItemIndex == index,
                onClick = {
                    onItemSelected(index)
                    when (item.screen) {
                        is RouteScreen -> navController.navigate(RouteScreen)
                        is MapScreen -> navController.navigate(MapScreen)
                        is WeatherScreen -> navController.navigate(WeatherScreen)
                    }
                },
                label = {
                    Text(text = item.title)
                },
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.title
                    )
                }
            )
        }
    }
}

















