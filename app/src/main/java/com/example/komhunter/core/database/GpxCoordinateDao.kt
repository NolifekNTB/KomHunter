package com.example.komhunter.core.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface GpxCoordinateDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(coordinates: List<GpxCoordinate>)

    @Query("SELECT * FROM gpx_coordinates")
    suspend fun getAllCoordinates(): List<GpxCoordinate>

    @Query("DELETE FROM gpx_coordinates")
    suspend fun deleteAll()
}