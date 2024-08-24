package com.example.komhunter.UI.core

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.komhunter.Core.ui.navigation.BottomNavMenu
import com.example.komhunter.Core.ui.navigation.models.HomeScreen
import com.example.komhunter.Core.ui.navigation.models.ProfileScreen
import com.example.komhunter.Core.ui.navigation.models.RouteScreen
import com.example.komhunter.Home.HomeScreen
import com.example.komhunter.Profile.ProfileScreen
import com.example.komhunter.Route.UploadGPX.ui.GpxFilePicker
import okhttp3.Route
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BottomNavMenuTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testBottomNavMenuClicks() {
        composeTestRule.setContent {
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = HomeScreen
            ) {
                composable<HomeScreen>  { HomeScreen() }
                composable<RouteScreen> { GpxFilePicker() {} }
                composable<ProfileScreen> { ProfileScreen() }
            }

            BottomNavMenu(
                navController = navController,
                selectedItemIndex = 0,
                onItemSelected = {  }
            )
        }

        composeTestRule.onNodeWithText("Route").performClick()
        composeTestRule.onNodeWithText("Select GPX File").assertExists()

        composeTestRule.onNodeWithText("Profile").performClick()
        composeTestRule.onNodeWithText("ProfileScreen").assertExists()

        composeTestRule.onNodeWithText("Home").performClick()
        composeTestRule.onNodeWithText("HomeScreen").assertExists()
    }
}