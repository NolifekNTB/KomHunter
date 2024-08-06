package com.example.komhunter.core.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.komhunter.core.data.database.daos.GpxCoordinateDao
import com.example.komhunter.core.data.database.daos.WeatherDao
import com.example.komhunter.core.data.database.entities.GpxCoordinate
import com.example.komhunter.core.data.database.entities.WeatherDataEntity

@Database(entities = [GpxCoordinate::class, WeatherDataEntity::class], version = 1)
abstract class MapDatabase : RoomDatabase() {
    abstract fun gpxCoordinateDao(): GpxCoordinateDao
    abstract fun weatherDao(): WeatherDao

    companion object {
        @Volatile
        private var INSTANCE: MapDatabase? = null

        fun getDatabase(context: Context): MapDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MapDatabase::class.java,
                    "gpx_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}