package com.example.komhunter.Route.Weather.domain.UseCases

import kotlin.math.cos

fun calculateEffectiveWindSpeed(
    segmentBearing: Double,
    windDirection: Double,
    windSpeed: Double
): Double {
    val angleDifference = (windDirection - segmentBearing).toNormalizedRadians()

    return windSpeed * cos(angleDifference)
}

private fun Double.toNormalizedRadians(): Double {
    return Math.toRadians((this + 360) % 360)
}