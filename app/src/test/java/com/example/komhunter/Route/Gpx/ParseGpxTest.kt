package com.example.komhunter.Route.Gpx

import com.example.komhunter.Route.UploadGPX.data.models.GpxCoordinate
import com.example.komhunter.Route.UploadGPX.domain.parseGpx
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.ByteArrayInputStream
import java.io.InputStream


@RunWith(RobolectricTestRunner::class)
class ParseGpxTest {

    @Test
    fun `parseGpx should return correct coordinates`() = runTest {
        // Sample GPX data as a string
        val gpxData = """
            <gpx>
                <trkpt lat="45.0" lon="7.0">
                    <ele>100.0</ele>
                </trkpt>
                <trkpt lat="46.0" lon="8.0">
                    <ele>200.0</ele>
                </trkpt>
            </gpx>
        """.trimIndent()

        // Mock the InputStream
        val inputStream: InputStream = ByteArrayInputStream(gpxData.toByteArray(Charsets.UTF_8))

        // Test the function
        val coordinates = parseGpx(inputStream)

        // Define expected output
        val expectedCoordinates = listOf(
            GpxCoordinate(latitude = 45.0, longitude = 7.0, elevation = 100.0),
            GpxCoordinate(latitude = 46.0, longitude = 8.0, elevation = 200.0)
        )

        // Assert the results
        assertEquals(expectedCoordinates, coordinates)
    }
}