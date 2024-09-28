package com.example.komhunter.route.gpx

import android.content.Context
import com.example.komhunter.core.data.local.WeatherDatabase
import com.example.komhunter.core.data.local.daos.GpxCoordinatesDao
import com.example.komhunter.route.uploadGPX.data.models.GpxCoordinate
import com.example.komhunter.route.uploadGPX.data.repositories.GpxRepository
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkObject
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GpxRepositoryTest {
    private lateinit var repository: GpxRepository
    private lateinit var gpxDao: GpxCoordinatesDao
    private lateinit var context: Context

    @Before
    fun setup() {
        context = mockk()
        gpxDao = mockk()

        mockkObject(WeatherDatabase.Companion)
        every { WeatherDatabase.getDatabase(context).gpxCoordinateDao() } returns gpxDao
        repository = GpxRepository(context)
    }

    @Test
    fun `test insertAll calls gpxDao insertAll`() =
        runBlocking {
            // Given
            val coordinates =
                listOf(
                    GpxCoordinate(latitude = 40.7128, longitude = -74.0060, elevation = 10.0),
                    GpxCoordinate(latitude = 34.0522, longitude = -118.2437, elevation = 15.0),
                )

            coEvery { gpxDao.insertAll(coordinates) } just Runs

            // When
            repository.insertAll(coordinates)

            // Then
            coVerify { gpxDao.insertAll(coordinates) }
        }

    @Test
    fun `test getAllCoordinates returns flow of coordinates`() =
        runBlocking {
            // Given
            val coordinates =
                listOf(
                    GpxCoordinate(latitude = 40.7128, longitude = -74.0060, elevation = 10.0),
                    GpxCoordinate(latitude = 34.0522, longitude = -118.2437, elevation = 15.0),
                )

            every { gpxDao.getAllCoordinates() } returns flowOf(coordinates)

            // When
            val result: Flow<List<GpxCoordinate>> = repository.getAllCoordinates()

            // Then
            result.collect { list ->
                assertEquals(coordinates, list)
            }
        }

    @Test
    fun `test deleteAll calls gpxDao deleteAll`() =
        runBlocking {
            // Given
            coEvery { gpxDao.deleteAll() } just Runs

            // When
            repository.deleteAll()

            // Then
            coVerify { gpxDao.deleteAll() }
        }
}
