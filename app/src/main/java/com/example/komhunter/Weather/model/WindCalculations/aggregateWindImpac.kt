package com.example.komhunter.Weather.model.WindCalculations

import com.example.komhunter.core.data.database.entities.GpxCoordinate
import com.example.komhunter.core.data.database.entities.WeatherDataEntity

fun aggregateWindImpact(
    coordinates: List<GpxCoordinate>,
    weatherData: WeatherDataEntity
): Double {
    val segments = divideRouteIntoSegments(coordinates)
    var totalWindImpact = 0.0

    segments.forEach { (start, end) ->
        val segmentBearing = calculateBearing(start, end)

        val effectiveWindSpeed = calculateEffectiveWindSpeed(
            segmentBearing,
            weatherData.wind_deg.toDouble(),
            weatherData.wind_speed
        ) * 3.6
        totalWindImpact += effectiveWindSpeed
    }

    return totalWindImpact / segments.size
}








