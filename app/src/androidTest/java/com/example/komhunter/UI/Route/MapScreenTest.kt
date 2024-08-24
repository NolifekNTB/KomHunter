package com.example.komhunter.UI.Route

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.runner.RunWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.compose.ui.viewinterop.AndroidView
import com.example.komhunter.Route.Maps.ui.DisplayMapContent
import com.example.komhunter.Route.Maps.ui.NavigationFab
import com.example.komhunter.Route.Maps.ui.rememberMapViewWithLifecycle
import org.junit.Rule
import org.junit.Test
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.osmdroid.util.BoundingBox
import org.osmdroid.views.overlay.Polyline

@RunWith(AndroidJUnit4::class)
class MapScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testMapViewDisplayed() {
        composeTestRule.setContent {
            val context = LocalContext.current
            val mapView = rememberMapViewWithLifecycle(context)
            val fakeBoundingBox: StateFlow<BoundingBox?> = MutableStateFlow(null)
            val fakePolyline: Polyline? = null

            DisplayMapContent(mapView, fakePolyline, fakeBoundingBox.value)

            Box(modifier = Modifier.fillMaxSize()) {
                AndroidView(
                    modifier = Modifier.fillMaxSize(),
                    factory = { mapView }
                )
                NavigationFab({}, Modifier.align(Alignment.BottomEnd))
            }
        }

        composeTestRule
            .onNodeWithContentDescription("Show Weather Data")
            .assertExists()
            .assertIsDisplayed()
    }

    @Test
    fun testNavigationFabClick() {
        var navigateClicked = false

        composeTestRule.setContent {
            val context = LocalContext.current
            val mapView = rememberMapViewWithLifecycle(context)
            val fakeBoundingBox: StateFlow<BoundingBox?> = MutableStateFlow(null)
            val fakePolyline: Polyline? = null

            DisplayMapContent(mapView, fakePolyline, fakeBoundingBox.value)

            Box(modifier = Modifier.fillMaxSize()) {
                AndroidView(
                    modifier = Modifier.fillMaxSize(),
                    factory = { mapView }
                )
                NavigationFab({ navigateClicked = true }, Modifier.align(Alignment.BottomEnd))
            }
        }

        composeTestRule
            .onNodeWithContentDescription("Show Weather Data")
            .performClick()

        assert(navigateClicked)
    }
}