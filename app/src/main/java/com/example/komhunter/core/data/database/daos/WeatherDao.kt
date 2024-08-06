package com.example.komhunter.core.data.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.komhunter.core.data.database.entities.WeatherDataEntity

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(weatherData: List<WeatherDataEntity>)

    @Query("SELECT * FROM weather_data")
    suspend fun getAllWeatherData(): List<WeatherDataEntity>
}