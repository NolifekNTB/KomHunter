package com.example.komhunter.core.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.komhunter.core.ui.navigation.BottomNavMenu
import com.example.komhunter.core.ui.navigation.NavigationHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNavigation()
        }
    }
}

@Composable
fun AppNavigation(navController: NavHostController = rememberNavController()) {
    var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }

    Scaffold(
        bottomBar = {
            BottomNavMenu(
                navController = navController,
                selectedItemIndex = selectedItemIndex,
            ) { index ->
                selectedItemIndex = index
            }
        },
    ) { innerPadding ->
        NavigationHost(navController = navController, padding = innerPadding)
    }
}
