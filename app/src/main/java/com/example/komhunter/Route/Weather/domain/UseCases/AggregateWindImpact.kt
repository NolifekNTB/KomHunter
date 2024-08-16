package com.example.komhunter.Route.Weather.domain.UseCases

import com.example.komhunter.Route.UploadGPX.data.models.GpxCoordinate
import com.example.komhunter.Route.Weather.data.models.WeatherData

fun aggregateWindImpact(
    coordinates: List<GpxCoordinate>,
    weatherData: WeatherData
): Double {
    val segments = divideRouteIntoSegments(coordinates)

    val totalWindImpact = segments.sumOf { segment ->
        calculateSegmentWindImpact(segment, weatherData)
    }
    return totalWindImpact / segments.size
}

fun divideRouteIntoSegments(coordinates: List<GpxCoordinate>): List<Pair<GpxCoordinate, GpxCoordinate>> {
    return coordinates.zipWithNext()
}

fun calculateSegmentWindImpact(
    segment: Pair<GpxCoordinate, GpxCoordinate>,
    weatherData: WeatherData
): Double {
    val segmentBearing = calculateBearing(segment.first, segment.second)
    val effectiveWindSpeed = calculateEffectiveWindSpeed(
        segmentBearing,
        weatherData.wind_deg.toDouble(),
        weatherData.wind_speed
    ) * 3.6
    return effectiveWindSpeed
}








