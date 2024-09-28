package com.example.benchmark

import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.By
import androidx.test.uiautomator.BySelector
import androidx.test.uiautomator.Until
import junit.framework.TestCase.fail
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class ExampleStartupBenchmark {
    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    private val packageName = "com.example.komhunter"

    /*@Test
    fun startup() = benchmarkRule.measureRepeated(
        packageName = packageName,
        metrics = listOf(StartupTimingMetric()),
        iterations = 3,
        startupMode = StartupMode.COLD
    ) {
        pressHome()
        startActivityAndWait()
    }
     */

    @Test
    fun benchmarkGpxFilePicker() {
        benchmarkRule.measureRepeated(
            packageName = packageName,
            metrics = listOf(FrameTimingMetric()),
            iterations = 1
        ) {
            setupBenchmark()
            clickButtonWithText("GpxFilePicker", "Select GPX File")
            selectFileFromPicker("Południe.gpx") // file needs to be created
            showWeatherPrediction()
        }
    }

    private fun MacrobenchmarkScope.setupBenchmark() {
        startActivityAndWait()

        clickButtonWithText("Route", "Route button")

        waitForUiElement(By.res( "GpxFilePicker"), "Select GPX File button")
    }

    private fun MacrobenchmarkScope.clickButtonWithText(resId: String, elementDescription: String) {
        val selector = By.res(resId)
        if (!device.wait(Until.hasObject(selector), 5_000)) {
            fail("Could not find '$elementDescription' in time")
        }
        device.findObject(selector).click()
    }

    private fun MacrobenchmarkScope.waitForUiElement(selector: BySelector, elementDescription: String) {
        if (!device.wait(Until.hasObject(selector), 5_000)) {
            fail("Could not find '$elementDescription' in time")
        }
    }

    private fun MacrobenchmarkScope.selectFileFromPicker(fileName: String) {
        val fileSelector = By.text(fileName)
        val fileToSelect = device.findObject(fileSelector)

        if (fileToSelect == null) {
            fail("Could not find the file '$fileName' in the picker")
        } else {
            fileToSelect.click()
        }

        device.waitForIdle(TimeUnit.SECONDS.toMillis(2))
    }

    private fun MacrobenchmarkScope.showWeatherPrediction() {
        val weatherSelector = By.res("WeatherDataFab")
        if (!device.wait(Until.hasObject(weatherSelector), 3_000)) {
            fail("Could not find 'WeatherDataFab' in time")
        }
        device.findObject(weatherSelector).click()
    }
}