package com.example.komhunter.Weather.model

import android.util.Log
import com.example.komhunter.core.data.database.entities.GpxCoordinate

fun divideRouteIntoSegments(coordinates: List<GpxCoordinate>): List<Pair<GpxCoordinate, GpxCoordinate>> {
    val segments = coordinates.zipWithNext()

    return segments
}