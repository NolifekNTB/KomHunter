package com.example.komhunter.route.weather.domain.usecases

import com.example.komhunter.route.uploadGPX.data.models.GpxCoordinate
import com.example.komhunter.route.weather.data.models.WeatherData

fun aggregateWindImpact(
    coordinates: List<GpxCoordinate>,
    weatherData: WeatherData,
): Double {
    val segments = divideRouteIntoSegments(coordinates)

    val totalWindImpact =
        segments.sumOf { segment ->
            calculateSegmentWindImpact(segment, weatherData)
        }
    return totalWindImpact / segments.size
}

fun divideRouteIntoSegments(coordinates: List<GpxCoordinate>): List<Pair<GpxCoordinate, GpxCoordinate>> = coordinates.zipWithNext()

fun calculateSegmentWindImpact(
    segment: Pair<GpxCoordinate, GpxCoordinate>,
    weatherData: WeatherData,
): Double {
    val segmentBearing = calculateBearing(segment.first, segment.second)

    val effectiveWindSpeed =
        calculateEffectiveWindSpeed(
            segmentBearing,
            weatherData.wind_deg.toDouble(),
            weatherData.wind_speed,
        ) * 3.6
    return effectiveWindSpeed
}
