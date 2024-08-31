package com.example.komhunter.Integration

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Instrumentation
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.testing.TestNavHostController
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.komhunter.Route.Maps.ui.DisplayMapContent
import com.example.komhunter.Route.Maps.ui.NavigationFab
import com.example.komhunter.Route.Maps.ui.rememberMapViewWithLifecycle
import com.example.komhunter.Route.UploadGPX.ui.GpxFilePicker
import com.example.komhunter.Route.UploadGPX.ui.GpxViewModel
import com.example.komhunter.Route.Weather.ui.WeatherScreenContent
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.osmdroid.util.BoundingBox
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Polyline
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@RunWith(AndroidJUnit4::class)
    class TestRouteToMapToWeatherforecast {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val mockGpxViewModel = mockk<GpxViewModel>(relaxed = true)
    private lateinit var navController: TestNavHostController

    private val testTimestamp = 1625077800L
    private val testWindImpact = 15.0

    @Before
    fun setUp() {
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun testGpxPickToMapToWeatherFlow() {
        prepareTestFile()

        composeTestRule.setContent {
            initializeNavController(LocalContext.current)

            var currentStep by remember { mutableStateOf("RouteScreen") }

            handleNavigation(currentStep)
            setupNavHost { currentStep = it } }

            performGpxFileSelection()
            verifyGpxFileOperations()

            performNavigationToWeatherScreen()
            verifyWeatherScreenContent()

            cleanUpTestFile()
    }

    private fun prepareTestFile() {
        val targetContext = InstrumentationRegistry.getInstrumentation().targetContext
        val testFilePath = File(targetContext.getExternalFilesDir(null), "test/TestTrack.gpx")
        testFilePath.parentFile?.mkdirs()
        testFilePath.writeText(
            """
            <gpx>
                <trk>
                    <name>Test Track</name>
                    <trkseg>
                        <trkpt lat="52.5163" lon="13.3777">
                            <ele>34.4</ele>
                        </trkpt>
                    </trkseg>
                </trk>
            </gpx>
            """.trimIndent()
        )

        val fileUri = Uri.fromFile(testFilePath)
        val intentResult = Intent().setData(fileUri)
        val result = Instrumentation.ActivityResult(Activity.RESULT_OK, intentResult)
        Intents.intending(IntentMatchers.hasAction(Intent.ACTION_OPEN_DOCUMENT)).respondWith(result)
    }

    private fun initializeNavController(context: Context) {
        navController = TestNavHostController(context).apply {
            navigatorProvider.addNavigator(ComposeNavigator())
        }
    }

    @Composable
    private fun handleNavigation(currentStep: String) {
        LaunchedEffect(currentStep) {
            when (currentStep) {
                "MapScreen" -> navController.navigate("MapScreen")
                "WeatherScreen" -> navController.navigate("WeatherScreen")
            }
        }
    }

    @Composable
    private fun setupNavHost(
        onNavigate: (String) -> Unit
    ) {
        val context = LocalContext.current
        val mapView = rememberMapViewWithLifecycle(context)
        val bestTimeFlow by remember { mutableStateOf(testTimestamp to testWindImpact) }

        NavHost(navController = navController, startDestination = "RouteScreen") {
            composable("RouteScreen") {
                GpxFilePicker(mockGpxViewModel) {
                    onNavigate("MapScreen")
                }
            }

            composable("MapScreen") {
                DisplayMapContent(mapView, null, null)

                Box(modifier = Modifier.fillMaxSize()) {
                    AndroidView(
                        modifier = Modifier.fillMaxSize(),
                        factory = { mapView }
                    )
                    NavigationFab(
                        {
                            onNavigate("WeatherScreen")
                        },
                        Modifier.align(Alignment.BottomEnd)
                    )
                }
            }

            composable("WeatherScreen") {
                WeatherScreenContent(bestTimeFlow)
            }
        }
    }

    private fun performGpxFileSelection() {
        composeTestRule.onNodeWithText("Select GPX File").performClick()
    }

    private fun verifyGpxFileOperations() {
        coVerify { mockGpxViewModel.deleteAll() }
        coVerify { mockGpxViewModel.insertAll(any()) }
    }

    private fun performNavigationToWeatherScreen() {
        composeTestRule.onNodeWithTag("WeatherDataFab").performClick()
    }

    private fun verifyWeatherScreenContent() {
        val date = Date(testTimestamp * 1000)
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val expectedText = "Best time for KOM: ${sdf.format(date)}\nWind Impact: ${testWindImpact.toInt()} km/h"
        composeTestRule.onNodeWithText(expectedText).assertExists()
    }

    private fun cleanUpTestFile() {
        val targetContext = InstrumentationRegistry.getInstrumentation().targetContext
        val testFilePath = File(targetContext.getExternalFilesDir(null), "test/TestTrack.gpx")
        testFilePath.delete()
    }
}
