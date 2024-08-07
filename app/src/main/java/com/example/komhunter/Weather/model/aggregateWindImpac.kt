package com.example.komhunter.Weather.model

import com.example.komhunter.core.data.database.entities.GpxCoordinate
import com.example.komhunter.core.data.database.entities.WeatherDataEntity

fun aggregateWindImpact(
    coordinates: List<GpxCoordinate>,
    weatherData: List<WeatherDataEntity>
): Double {
    val segments = divideRouteIntoSegments(coordinates)
    var totalWindImpact = 0.0

    segments.forEach { segment ->
        val segmentBearing = calculateBearing(segment.first, segment.second)

        val closestWeather = weatherData[0]
        val effectiveWindSpeed = calculateEffectiveWindSpeed(
            segmentBearing,
            closestWeather.wind_deg.toDouble(),
            closestWeather.wind_speed
        )
        totalWindImpact += effectiveWindSpeed
    }

    return totalWindImpact / segments.size
}








