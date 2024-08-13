package com.example.komhunter.Route.UploadGPX.data.repositories

import android.content.Context
import com.example.komhunter.Route.UploadGPX.data.models.GpxCoordinate
import com.example.komhunter.Core.data.local.daos.GpxCoordinatesDao
import com.example.komhunter.Core.data.local.WeatherDatabase
import kotlinx.coroutines.flow.Flow

class GpxRepository(context: Context): GpxCoordinatesDao {
    private val gpxDao = WeatherDatabase.getDatabase(context).gpxCoordinateDao()

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