package com.example.komhunter.Route.UploadGPX.domain

import com.example.komhunter.Route.UploadGPX.data.models.GpxCoordinate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStream

suspend fun parseGpx(inputStream: InputStream): List<GpxCoordinate> {
    return withContext(Dispatchers.IO) {
        val coordinates = mutableListOf<GpxCoordinate>()
        val parser = createXmlPullParser(inputStream)

        var latitude: Double? = null
        var longitude: Double? = null
        var elevation: Double? = null

        while (parser.eventType != XmlPullParser.END_DOCUMENT) {
            when (parser.eventType) {
                XmlPullParser.START_TAG -> {
                    when (parser.name) {
                        "trkpt" -> {
                            latitude = parser.getAttributeValue(null, "lat")?.toDoubleOrNull()
                            longitude = parser.getAttributeValue(null, "lon")?.toDoubleOrNull()
                        }
                        "ele" -> {
                            elevation = parser.nextText().toDoubleOrNull()
                        }
                    }
                }
                XmlPullParser.END_TAG -> {
                    if (parser.name == "trkpt" && latitude != null && longitude != null) {
                        coordinates.add(
                            GpxCoordinate(
                                latitude = latitude,
                                longitude = longitude,
                                elevation = elevation
                            )
                        )
                        latitude = null
                        longitude = null
                        elevation = null
                    }
                }
            }
            parser.next()
        }
        coordinates
    }
}

private fun createXmlPullParser(inputStream: InputStream): XmlPullParser {
    return XmlPullParserFactory.newInstance().apply {
        isNamespaceAware = true
    }.newPullParser().apply {
        setInput(inputStream, null)
    }
}












