package com.example.komhunter.core

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.komhunter.core.ui.theme.KomHunterTheme
import com.example.komhunter.uploadGPX.data.GpxCoordinate
import com.example.komhunter.uploadGPX.ui.GpxFilePicker

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            KomHunterTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val coordinatesState = remember { mutableStateOf<List<GpxCoordinate>>(emptyList()) }

    Column {
        GpxFilePicker { coordinates ->
            coordinatesState.value = coordinates
        }

        LazyColumn {
            items(coordinatesState.value) { coordinate ->
                Text("Lat: ${coordinate.latitude}, Lon: ${coordinate.longitude}, Ele: ${coordinate.elevation}")
            }
        }
    }
}

