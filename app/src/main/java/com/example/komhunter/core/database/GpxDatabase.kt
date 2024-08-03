package com.example.komhunter.core.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [GpxCoordinate::class], version = 1)
abstract class GpxDatabase : RoomDatabase() {
    abstract fun gpxCoordinateDao(): GpxCoordinateDao

    companion object {
        @Volatile
        private var INSTANCE: GpxDatabase? = null

        fun getDatabase(context: Context): GpxDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GpxDatabase::class.java,
                    "gpx_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}