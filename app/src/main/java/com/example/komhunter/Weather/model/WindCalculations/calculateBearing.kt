package com.example.komhunter.Weather.model.WindCalculations

import com.example.komhunter.core.data.database.entities.GpxCoordinate
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

fun calculateBearing(start: GpxCoordinate, end: GpxCoordinate): Double {
    val lat1 = Math.toRadians(start.latitude)
    val lon1 = Math.toRadians(start.longitude)
    val lat2 = Math.toRadians(end.latitude)
    val lon2 = Math.toRadians(end.longitude)

    val dLon = lon2 - lon1

    val y = sin(dLon) * cos(lat2)
    val x = cos(lat1) * sin(lat2) - sin(lat1) * cos(lat2) * cos(dLon)

    val results = (Math.toDegrees(atan2(y, x)) + 360) % 360

    return results
}