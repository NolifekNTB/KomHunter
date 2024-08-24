package com.example.komhunter.UI.core

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.komhunter.Core.ui.AppNavigation
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppNavigationTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testNavigationBetweenScreens() {
        composeTestRule.setContent {
            val navController = rememberNavController()
            AppNavigation(navController)
        }

        composeTestRule.onNodeWithText("HomeScreen").assertExists()

        composeTestRule.onNodeWithText("Route").performClick()
        composeTestRule.onNodeWithText("Select GPX File").assertExists()

        composeTestRule.onNodeWithText("Profile").performClick()
        composeTestRule.onNodeWithText("ProfileScreen").assertExists()

        composeTestRule.onNodeWithText("Route").performClick()
    }
}