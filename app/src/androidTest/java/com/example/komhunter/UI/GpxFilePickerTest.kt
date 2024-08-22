package com.example.komhunter.UI

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.setContent
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.komhunter.Core.ui.MainActivity
import com.example.komhunter.Route.UploadGPX.data.models.GpxCoordinate
import com.example.komhunter.Route.UploadGPX.ui.GpxFilePicker
import com.example.komhunter.Route.UploadGPX.ui.GpxViewModel
import com.example.komhunter.Route.Weather.ui.WeatherScreen
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File

@RunWith(AndroidJUnit4::class)
class GpxFilePickerTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private val mockViewModel = mockk<GpxViewModel>(relaxed = true)

    @Before
    fun setUp() {
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun testGpxFilePicker_handlesFileCorrectly() = runTest {
        // Arrange
        // Create or use an existing file path
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val testFilePath = File(context.getExternalFilesDir(null), "test/Północ.gpx")

        // Ensure the file exists for the test
        testFilePath.parentFile?.mkdirs()
        testFilePath.writeText("""
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
        """.trimIndent())

        // Create a Uri for the file
        val fileUri = Uri.fromFile(testFilePath)

        // Set up the mocked ActivityResult
        val intentResult = Intent().setData(fileUri)
        val result = Instrumentation.ActivityResult(Activity.RESULT_OK, intentResult)

        // Intercept the intent
        Intents.intending(IntentMatchers.hasAction(Intent.ACTION_OPEN_DOCUMENT)).respondWith(result)

        // Act
        composeTestRule.activity.setContent {
            GpxFilePicker(viewModel = mockViewModel, onNavigate = {})
        }

        composeTestRule.onNodeWithText("Select GPX File").performClick()

        // Assert
        Intents.intended(IntentMatchers.hasAction(Intent.ACTION_OPEN_DOCUMENT))

        // Verify that the ViewModel methods were called
        coVerify { mockViewModel.deleteAll() }
        coVerify { mockViewModel.insertAll(any()) }

        // Clean up the test file
        testFilePath.delete()
    }
}