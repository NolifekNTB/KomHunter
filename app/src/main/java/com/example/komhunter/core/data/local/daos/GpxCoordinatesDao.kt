package com.example.komhunter.core.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.komhunter.route.uploadGPX.data.models.GpxCoordinate
import kotlinx.coroutines.flow.Flow

@Dao
interface GpxCoordinatesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(coordinates: List<GpxCoordinate>)

    @Query("SELECT * FROM gpx_coordinates")
    fun getAllCoordinates(): Flow<List<GpxCoordinate>>

    @Query("DELETE FROM gpx_coordinates")
    suspend fun deleteAll()
}
