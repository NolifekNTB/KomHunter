package com.example.komhunter.Route.Weather

import android.app.Application
import androidx.test.core.app.ApplicationProvider
import com.example.komhunter.Core.data.local.daos.WeatherDao
import com.example.komhunter.Route.Weather.data.models.WeatherData
import com.example.komhunter.Route.Weather.data.repositories.WeatherRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class WeatherRepositoryTest {
    private lateinit var repository: WeatherRepository
    private var context: Application? = null
    private val weatherDao = mock(WeatherDao::class.java)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        repository = WeatherRepository(context as Application)
        repository.weatherDao = weatherDao

        Dispatchers.setMain(StandardTestDispatcher())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testInsertAll() = runTest {
        // Given
        val weatherData = listOf(
            WeatherData(dt = 1, temp = 20.0),
            WeatherData(dt = 2, temp = 21.0)
        )

        // When
        repository.insertAll(weatherData)

        // Then
        verify(weatherDao, times(1)).insertAll(weatherData)
    }

    @Test
    fun testGetAllWeatherData() = runTest {
        // Given
        val weatherData = listOf(
            WeatherData(dt = 1, temp = 20.0),
            WeatherData(dt = 2, temp = 21.0)
        )

        `when`(weatherDao.getAllWeatherData()).thenReturn(weatherData)

        // When
        val result = repository.getAllWeatherData()

        // Then
        verify(weatherDao, times(1)).getAllWeatherData()
        assertEquals(weatherData, result)
    }
}

















