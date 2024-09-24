package com.example.komhunter.Core.ui.navigation

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.komhunter.R
import com.example.komhunter.Core.ui.navigation.models.BottomNavigationItem
import com.example.komhunter.Core.ui.navigation.models.HomeScreen
import com.example.komhunter.Core.ui.navigation.models.ProfileScreen
import com.example.komhunter.Core.ui.navigation.models.RouteScreen

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BottomNavMenu(
    navController: NavHostController,
    selectedItemIndex: Int,
    onItemSelected: (Int) -> Unit
) {
    val items = getBottomNavItems()

    NavigationBar(
        modifier = Modifier.semantics {
            testTagsAsResourceId = true
        }
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                modifier = Modifier.testTag(item.title),
                selected = selectedItemIndex == index,
                onClick = {
                    onItemSelected(index)
                    navController.navigate(item.screen)
                },
                label = { Text(text = item.title) },
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

@Composable
private fun getBottomNavItems(): List<BottomNavigationItem> {
    return listOf(
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
}











