package com.example.komhunter.uploadGPX.data

import android.content.Context
import com.example.komhunter.core.data.database.entities.GpxCoordinate
import com.example.komhunter.core.data.database.daos.GpxCoordinateDao
import com.example.komhunter.core.data.database.MapDatabase
import kotlinx.coroutines.flow.Flow

class GpxRepository(context: Context): GpxCoordinateDao {
    private val gpxDao = MapDatabase.getDatabase(context).gpxCoordinateDao()

    override suspend fun insertAll(coordinates: List<GpxCoordinate>) {
        gpxDao.insertAll(coordinates)
    }

    override fun getAllCoordinates(): Flow<List<GpxCoordinate>> {
        return gpxDao.getAllCoordinates()
    }

    override suspend fun deleteAll() {
        gpxDao.deleteAll()
    }
}