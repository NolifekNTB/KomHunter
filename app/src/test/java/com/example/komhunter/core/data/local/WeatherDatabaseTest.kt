package com.example.komhunter.core.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.komhunter.Core.data.local.WeatherDatabase
import junit.framework.Assert.assertNotNull
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class WeatherDatabaseTest {

    private lateinit var database: WeatherDatabase
    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        database = Room.inMemoryDatabaseBuilder(
            context, WeatherDatabase::class.java
        ).build()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun testGetDatabaseInstance() {
        // Test that the database instance is not null
        val dbInstance = WeatherDatabase.getDatabase(context)
        assertNotNull(dbInstance)
    }

    @Test
    fun testSingletonPattern() {
        // Test that the singleton pattern works and the same instance is returned
        val dbInstance1 = WeatherDatabase.getDatabase(context)
        val dbInstance2 = WeatherDatabase.getDatabase(context)
        assert(dbInstance1 === dbInstance2) // Use === to check reference equality
    }

    @Test
    fun testGpxCoordinateDao() {
        // Test that the GpxCoordinatesDao is not null
        val gpxDao = database.gpxCoordinateDao()
        assertNotNull(gpxDao)
    }

    @Test
    fun testWeatherDao() {
        // Test that the WeatherDao is not null
        val weatherDao = database.weatherDao()
        assertNotNull(weatherDao)
    }
}