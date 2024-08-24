package com.example.komhunter.Route.Maps

import android.content.Context
import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.example.komhunter.Route.Maps.ui.DisplayMapContent
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.osmdroid.util.BoundingBox
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Polyline
import io.mockk.verify
import org.osmdroid.util.GeoPoint


class MapScreenPolylineTest {

    private lateinit var context: Context
    private lateinit var mockMapView: MapView

    @Before
    fun setup() {
        context = mockk(relaxed = true)

        mockMapView = mockk(relaxed = true)

        every { mockMapView.overlays } returns mutableListOf()
        every { mockMapView.zoomToBoundingBox(any(), any()) } returns Unit
        every { mockMapView.invalidate() } returns Unit
    }

    @get:Rule
    val paparazzi = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_5
    )

    @Test
    fun testPolylineAddedToMapWithoutVisualAssertion() {
        val boundingBox = BoundingBox(1.0, 1.0, 2.0, 2.0)
        val polyLine = Polyline().apply {
            setPoints(listOf(
                GeoPoint(1.0, 1.0),
                GeoPoint(2.0, 2.0)
            )
            )
        }

        val slot = slot<Polyline>()
        every { mockMapView.overlays.add(capture(slot)) } returns true

        paparazzi.snapshot {
            DisplayMapContent(mockMapView, polyLine, boundingBox)
        }

        assert(slot.captured == polyLine)

        verify { mockMapView.zoomToBoundingBox(boundingBox, true) }

        verify { mockMapView.invalidate() }
    }
}