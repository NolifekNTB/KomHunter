package com.example.komhunter.UI.core

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.komhunter.Core.ui.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testMainActivityDisplaysCorrectly() {
        composeTestRule.onNodeWithText("Homee").assertExists()

        composeTestRule.onNodeWithText("Route").performClick()
        composeTestRule.onNodeWithText("Select GPX File").assertExists()
    }
}