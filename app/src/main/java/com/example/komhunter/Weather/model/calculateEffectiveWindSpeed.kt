package com.example.komhunter.Weather.model

import android.util.Log
import kotlin.math.cos

fun calculateEffectiveWindSpeed(
    segmentBearing: Double,
    windDirection: Double,
    windSpeed: Double
): Double {
    val angleDifference = Math.toRadians((windDirection - segmentBearing + 360) % 360)

    return windSpeed * cos(angleDifference)
}