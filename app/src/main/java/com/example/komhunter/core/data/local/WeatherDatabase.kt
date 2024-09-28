package com.example.komhunter.core.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.komhunter.core.data.local.daos.GpxCoordinatesDao
import com.example.komhunter.core.data.local.daos.WeatherDao
import com.example.komhunter.route.uploadGPX.data.models.GpxCoordinate
import com.example.komhunter.route.weather.data.models.WeatherData

@Database(entities = [GpxCoordinate::class, WeatherData::class], version = 1)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun gpxCoordinateDao(): GpxCoordinatesDao

    abstract fun weatherDao(): WeatherDao

    companion object {
        @Volatile
        private var instance: WeatherDatabase? = null

        fun getDatabase(context: Context): WeatherDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }

        private fun buildDatabase(context: Context) =
            Room
                .databaseBuilder(
                    context.applicationContext,
                    WeatherDatabase::class.java,
                    "weather_database",
                ).build()
    }
}
