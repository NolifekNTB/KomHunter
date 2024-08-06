package com.example.komhunter.core.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import org.osmdroid.config.Configuration
import androidx.navigation.compose.rememberNavController
import com.example.komhunter.core.ui.navigation.AppNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Column(Modifier.fillMaxSize()) {
                AppNavigation(navController = rememberNavController())
            }
        }
    }
}