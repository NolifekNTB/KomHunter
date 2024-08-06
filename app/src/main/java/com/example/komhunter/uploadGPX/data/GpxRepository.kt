package com.example.komhunter.uploadGPX.data

import android.content.Context
import com.example.komhunter.core.data.database.GpxCoordinate
import com.example.komhunter.core.data.database.GpxCoordinateDao
import com.example.komhunter.core.data.database.GpxDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class GpxRepository(context: Context): GpxCoordinateDao {
    private val gpxDao = GpxDatabase.getDatabase(context).gpxCoordinateDao()

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