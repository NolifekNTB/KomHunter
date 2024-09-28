package com.example.komhunter.core.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
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
        database =
            Room
                .inMemoryDatabaseBuilder(
                    context,
                    WeatherDatabase::class.java,
                ).build()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun testGetDatabaseInstance() {
        val dbInstance = WeatherDatabase.getDatabase(context)
        assertNotNull(dbInstance)
    }

    @Test
    fun testSingletonPattern() {
        val dbInstance1 = WeatherDatabase.getDatabase(context)
        val dbInstance2 = WeatherDatabase.getDatabase(context)
        assert(dbInstance1 === dbInstance2) // Use === to check reference equality
    }

    @Test
    fun testGpxCoordinateDao() {
        val gpxDao = database.gpxCoordinateDao()
        assertNotNull(gpxDao)
    }

    @Test
    fun testWeatherDao() {
        val weatherDao = database.weatherDao()
        assertNotNull(weatherDao)
    }
}
