package com.example.komhunter.UI.Route

import androidx.compose.runtime.collectAsState
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.runner.RunWith
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.komhunter.Route.Weather.ui.WeatherScreenContent
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*


@RunWith(AndroidJUnit4::class)
class WeatherScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testLoadingState() {
        val bestTimeFlow = MutableStateFlow<Pair<Long, Double>?>(null)

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
        val testTimestamp = 1625077800L // This is an example timestamp
        val testWindImpact = 15.0

        val bestTimeFlow = MutableStateFlow(testTimestamp to testWindImpact)

        composeTestRule.setContent {
            val bestTime = bestTimeFlow.collectAsState()

            WeatherScreenContent(bestTime.value)
        }

        val date = Date(testTimestamp * 1000)
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val expectedText = "Best time for KOM: ${sdf.format(date)}\nWind Impact: ${testWindImpact.toInt()} km/h"

        composeTestRule
            .onNodeWithText(expectedText)
            .assertIsDisplayed()
    }

}