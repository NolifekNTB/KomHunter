package com.example.komhunter.Route.UploadGPX.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "gpx_coordinates")
data class GpxCoordinate(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val latitude: Double,
    val longitude: Double,
    val elevation: Double?
)