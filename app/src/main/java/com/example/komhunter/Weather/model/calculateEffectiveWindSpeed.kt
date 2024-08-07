package com.example.komhunter.Weather.model

import android.util.Log
import kotlin.math.cos

fun calculateEffectiveWindSpeed(
    segmentBearing: Double,
    windDirection: Double,
    windSpeed: Double
): Double {
    val angleDifference = Math.toRadians((windDirection - segmentBearing + 360) % 360)

    Log.d("EffectiveWindSpeed", "Angle difference: $angleDifference")
    Log.d("EffectiveWindSpeed", "Angle speed: ${15 * cos(angleDifference)}")
    return 15 * cos(angleDifference)
}