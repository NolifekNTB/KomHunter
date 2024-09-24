package com.example.komhunter.UI.Route

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
import com.example.komhunter.Route.UploadGPX.ui.GpxFilePicker
import com.example.komhunter.Route.UploadGPX.ui.GpxViewModel
import io.mockk.coVerify
import io.mockk.mockk
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
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val testFilePath = File(context.getExternalFilesDir(null), "test/Północ.gpx")

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

        val fileUri = Uri.fromFile(testFilePath)

        val intentResult = Intent().setData(fileUri)
        val result = Instrumentation.ActivityResult(Activity.RESULT_OK, intentResult)

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
        coVerify { mockViewModel.insertAll(any(), any()) }

        // Clean up the test file
        testFilePath.delete()
    }
}