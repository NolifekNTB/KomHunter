package com.example.komhunter.Core.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.compose.rememberNavController
import com.example.komhunter.Core.ui.navigation.NavigationHost
import com.example.komhunter.Core.ui.navigation.BottomNavMenu

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNavigation()
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }

    Scaffold(
        bottomBar = {
            BottomNavMenu(
                navController = navController,
                selectedItemIndex = selectedItemIndex
            ) { index ->
                selectedItemIndex = index
        } }
    ) { innerPadding ->
        NavigationHost(navController = navController, padding = innerPadding)
    }
}

