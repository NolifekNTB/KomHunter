package com.example.komhunter.UI

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
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Polyline

@RunWith(AndroidJUnit4::class)
class MapScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testMapViewDisplayed() {
        // Set the Composable to be tested with a fake ViewModel
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
                // Add a simple FAB that does nothing for this test
                NavigationFab({}, Modifier.align(Alignment.BottomEnd))
            }
        }

        // Check that the MapView is displayed by verifying the presence of the FAB
        composeTestRule
            .onNodeWithContentDescription("Show Weather Data")
            .assertExists()
            .assertIsDisplayed()
    }

    @Test
    fun testNavigationFabClick() {
        var navigateClicked = false

        // Set the Composable to be tested with a fake ViewModel
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

        // Click on the FAB
        composeTestRule
            .onNodeWithContentDescription("Show Weather Data")
            .performClick()

        // Verify that the navigation callback was triggered
        assert(navigateClicked)
    }
}