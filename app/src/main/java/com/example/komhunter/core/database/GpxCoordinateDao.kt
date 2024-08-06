package com.example.komhunter.core.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

@Dao
interface GpxCoordinateDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(coordinates: List<GpxCoordinate>)

    @Query("SELECT * FROM gpx_coordinates")
    fun getAllCoordinates(): Flow<List<GpxCoordinate>>

    @Query("DELETE FROM gpx_coordinates")
    suspend fun deleteAll()
}