package com.example.komhunter.route.weather.domain.usecases

import com.example.komhunter.route.uploadGPX.data.models.GpxCoordinate
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

fun calculateBearing(
    start: GpxCoordinate,
    end: GpxCoordinate,
): Double {
    val lat1 = Math.toRadians(start.latitude)
    val lon1 = Math.toRadians(start.longitude)
    val lat2 = Math.toRadians(end.latitude)
    val lon2 = Math.toRadians(end.longitude)

    val dLon = lon2 - lon1

    val y = sin(dLon) * cos(lat2)
    val x = cos(lat1) * sin(lat2) - sin(lat1) * cos(lat2) * cos(dLon)

    return atan2(y, x).toDegrees().normalizeBearing()
}

private fun Double.toDegrees(): Double = Math.toDegrees(this)

private fun Double.normalizeBearing(): Double = (this + 360) % 360
