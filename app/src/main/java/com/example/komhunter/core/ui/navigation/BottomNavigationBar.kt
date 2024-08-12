package com.example.komhunter.core.ui.navigation

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.komhunter.R
import okhttp3.Route

@Composable
fun BottomNavigationBar(navController: NavHostController, selectedItemIndex: Int, onItemSelected: (Int) -> Unit) {
    val items = listOf(
        BottomNavigationItem(
            screen = HomeScreen,
            title = "Home",
            icon = R.drawable.home
        ),
        BottomNavigationItem(
            screen = RouteScreen,
            title = "Route",
            icon = R.drawable.route
        ),
        BottomNavigationItem(
            screen = ProfileScreen,
            title = "Profile",
            icon = R.drawable.profile
        ),
    )

    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItemIndex == index,
                onClick = {
                    onItemSelected(index)
                    when (item.screen) {
                        is HomeScreen -> navController.navigate(HomeScreen)
                        is RouteScreen -> navController.navigate(RouteScreen)
                        is ProfileScreen -> navController.navigate(ProfileScreen)
                    }
                },
                label = {
                    Text(text = item.title)
                },
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.title,
                        modifier = Modifier.size(24.dp)
                    )
                }
            )
        }
    }
}

















