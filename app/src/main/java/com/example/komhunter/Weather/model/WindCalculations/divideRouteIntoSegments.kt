package com.example.komhunter.Weather.model.WindCalculations

import com.example.komhunter.core.data.database.entities.GpxCoordinate

fun divideRouteIntoSegments(coordinates: List<GpxCoordinate>): List<Pair<GpxCoordinate, GpxCoordinate>> {
    val segments = coordinates.zipWithNext()

    return segments
}