package com.example.komhunter.Route.Gpx

import com.example.komhunter.Route.UploadGPX.data.models.GpxCoordinate
import com.example.komhunter.Route.UploadGPX.domain.parseGpx
import org.junit.Assert.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.io.ByteArrayInputStream
import java.io.InputStream


@RunWith(RobolectricTestRunner::class)
class ParseGpxTest {

    @Test
    fun `parseGpx should return correct coordinates`() = runTest {
        val sampleGpxData = """
            <gpx>
                <trkpt lat="45.0" lon="7.0">
                    <ele>100.0</ele>
                </trkpt>
                <trkpt lat="46.0" lon="8.0">
                    <ele>200.0</ele>
                </trkpt>
            </gpx>
        """.trimIndent()

        val inputStream: InputStream = ByteArrayInputStream(sampleGpxData.toByteArray(Charsets.UTF_8))

        val coordinates = parseGpx(inputStream)

        val expectedCoordinates = listOf(
            GpxCoordinate(latitude = 45.0, longitude = 7.0, elevation = 100.0),
            GpxCoordinate(latitude = 46.0, longitude = 8.0, elevation = 200.0)
        )

        assertEquals(expectedCoordinates, coordinates)
    }
}