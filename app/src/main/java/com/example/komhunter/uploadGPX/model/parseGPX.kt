package com.example.komhunter.uploadGPX.model

import com.example.komhunter.core.database.GpxCoordinate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStream

suspend fun parseGpx(inputStream: InputStream): List<GpxCoordinate> {
    return withContext(Dispatchers.IO) {
        val coordinates = mutableListOf<GpxCoordinate>()
        val factory = XmlPullParserFactory.newInstance()
        factory.isNamespaceAware = true
        val parser = factory.newPullParser()
        parser.setInput(inputStream, null)

        var eventType = parser.eventType
        var latitude: Double? = null
        var longitude: Double? = null
        var elevation: Double? = null

        while (eventType != XmlPullParser.END_DOCUMENT) {
            val name = parser.name
            when (eventType) {
                XmlPullParser.START_TAG -> {
                    when (name) {
                        "trkpt" -> {
                            latitude = parser.getAttributeValue(null, "lat").toDouble()
                            longitude = parser.getAttributeValue(null, "lon").toDouble()
                        }
                        "ele" -> {
                            elevation = parser.nextText().toDouble()
                        }
                    }
                }
                XmlPullParser.END_TAG -> {
                    if (name == "trkpt" && latitude != null && longitude != null) {
                        coordinates.add(GpxCoordinate(
                            latitude = latitude,
                            longitude = longitude,
                            elevation = elevation
                        ))
                        latitude = null
                        longitude = null
                        elevation = null
                    }
                }
            }
            eventType = parser.next()
        }
        coordinates
    }
}