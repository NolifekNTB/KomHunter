package com.example.komhunter.route.uploadGPX.data.repositories

import android.content.Context
import com.example.komhunter.core.data.local.WeatherDatabase
import com.example.komhunter.core.data.local.daos.GpxCoordinatesDao
import com.example.komhunter.route.uploadGPX.data.models.GpxCoordinate
import kotlinx.coroutines.flow.Flow

class GpxRepository(
    context: Context,
) : GpxCoordinatesDao {
    private val gpxDao = WeatherDatabase.getDatabase(context).gpxCoordinateDao()

    override suspend fun insertAll(coordinates: List<GpxCoordinate>) {
        gpxDao.insertAll(coordinates)
    }

    override fun getAllCoordinates(): Flow<List<GpxCoordinate>> = gpxDao.getAllCoordinates()

    override suspend fun deleteAll() {
        gpxDao.deleteAll()
    }
}
