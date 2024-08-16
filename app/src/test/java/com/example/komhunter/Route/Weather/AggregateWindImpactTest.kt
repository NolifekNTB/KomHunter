package com.example.komhunter.Route.Weather

import com.example.komhunter.Route.UploadGPX.data.models.GpxCoordinate
import com.example.komhunter.Route.Weather.data.models.WeatherData
import com.example.komhunter.Route.Weather.domain.UseCases.aggregateWindImpact
import com.example.komhunter.Route.Weather.domain.UseCases.calculateBearing
import com.example.komhunter.Route.Weather.domain.UseCases.calculateEffectiveWindSpeed
import com.example.komhunter.Route.Weather.domain.UseCases.calculateSegmentWindImpact
import com.example.komhunter.Route.Weather.domain.UseCases.divideRouteIntoSegments
import junit.framework.TestCase.assertEquals
import org.junit.Test

class AggregateWindImpactTest {

    @Test
    fun testAggregateWindImpact() {
        // Given
        val coordinates = listOf(
            GpxCoordinate(latitude = 52.5200, longitude = 13.4050, elevation = 10.0), // Berlin
            GpxCoordinate(latitude = 48.8566, longitude =  2.3522, elevation = 10.0),  // Paris
            GpxCoordinate(latitude = 51.5074, longitude = -0.1278, elevation = 10.0)  // London
        )

        val weatherData = WeatherData(
            wind_speed = 10.0,
            wind_deg = 90
        )

        // When
        val result = aggregateWindImpact(coordinates, weatherData)

        // Then
        val bearing1 = calculateBearing(coordinates[0], coordinates[1])
        val windImpact1 = calculateEffectiveWindSpeed(bearing1, weatherData.wind_deg.toDouble(), weatherData.wind_speed) * 3.6
        val bearing2 = calculateBearing(coordinates[1], coordinates[2])
        val windImpact2 = calculateEffectiveWindSpeed(bearing2, weatherData.wind_deg.toDouble(), weatherData.wind_speed) * 3.6
        val expectedTotalWindImpact = (windImpact1 + windImpact2) / 2.0

        assertEquals(expectedTotalWindImpact, result, 0.01)
    }

    @Test
    fun testDivideRouteIntoSegments() {
        // Given
        val coordinates = listOf(
            GpxCoordinate(latitude = 52.5200, longitude = 13.4050, elevation = 10.0), // Berlin
            GpxCoordinate(latitude = 48.8566, longitude =  2.3522, elevation = 10.0),  // Paris
            GpxCoordinate(latitude = 51.5074, longitude = -0.1278, elevation = 10.0)  // London
        )

        // When
        val result = divideRouteIntoSegments(coordinates)

        // Then
        val expectedSegments = listOf(
            Pair(
                GpxCoordinate(latitude = 52.5200, longitude = 13.4050, elevation = 10.0),
                GpxCoordinate(latitude = 48.8566, longitude =  2.3522, elevation = 10.0)
            ),
            Pair(
                GpxCoordinate(latitude = 48.8566, longitude =  2.3522, elevation = 10.0),
                GpxCoordinate(latitude = 51.5074, longitude = -0.1278, elevation = 10.0)
            )
        )
        assertEquals(expectedSegments, result)
    }

    @Test
    fun testCalculateSegmentWindImpact() {
        // Given
        val segment = Pair(
            GpxCoordinate(latitude = 52.5200, longitude = 13.4050, elevation = 10.0), // Berlin
            GpxCoordinate(latitude = 48.8566, longitude =  2.3522, elevation = 10.0),  // Paris
        )
        val weatherData = WeatherData(
            wind_speed = 10.0,
            wind_deg = 90
        )

        // When
        val result = calculateSegmentWindImpact(segment, weatherData)

        // Then
        val expectedWindImpact = -33.07
        assertEquals(expectedWindImpact, result, 0.01)
    }
}