package com.example.komhunter.UI

import androidx.compose.runtime.collectAsState
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.runner.RunWith
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.komhunter.Route.Weather.ui.WeatherScreenContent
import com.example.komhunter.Route.Weather.ui.WeatherViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.junit.Rule
import org.junit.Test
import io.mockk.coEvery
import java.text.SimpleDateFormat
import java.util.*


@RunWith(AndroidJUnit4::class)
class WeatherScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testLoadingState() {
        // Create a MutableStateFlow to simulate the ViewModel's bestTime flow
        val bestTimeFlow = MutableStateFlow<Pair<Long, Double>?>(null)

        // Set the Composable to be tested with a fake ViewModel
        composeTestRule.setContent {
            val bestTime = bestTimeFlow.collectAsState()

            WeatherScreenContent(bestTime.value)
        }

        // Assert that the loading text is shown
        composeTestRule
            .onNodeWithText("Loading...")
            .assertIsDisplayed()
    }

    @Test
    fun testBestTimeContentDisplayed() {
        // Create a specific timestamp and wind impact to display
        val testTimestamp = 1625077800L // This is an example timestamp
        val testWindImpact = 15.0

        // Create a MutableStateFlow with the test data
        val bestTimeFlow = MutableStateFlow(testTimestamp to testWindImpact)

        // Set the Composable to be tested with a fake ViewModel
        composeTestRule.setContent {
            val bestTime = bestTimeFlow.collectAsState()

            WeatherScreenContent(bestTime.value)
        }

        // Convert the timestamp to a human-readable format
        val date = Date(testTimestamp * 1000)
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val expectedText = "Best time for KOM: ${sdf.format(date)}\nWind Impact: ${testWindImpact.toInt()} km/h"

        // Assert that the correct best time content is shown
        composeTestRule
            .onNodeWithText(expectedText)
            .assertIsDisplayed()
    }

}