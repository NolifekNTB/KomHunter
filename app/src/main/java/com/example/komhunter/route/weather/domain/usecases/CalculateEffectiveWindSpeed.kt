package com.example.komhunter.route.weather.domain.usecases

import kotlin.math.cos

fun calculateEffectiveWindSpeed(
    segmentBearing: Double,
    windDirection: Double,
    windSpeed: Double,
): Double {
    val adjustedWindDirection = (windDirection + 180) % 360

    val angleDifference = (adjustedWindDirection - segmentBearing).toNormalizedRadians()

    return windSpeed * cos(angleDifference)
}

private fun Double.toNormalizedRadians(): Double = Math.toRadians((this + 360) % 360)
