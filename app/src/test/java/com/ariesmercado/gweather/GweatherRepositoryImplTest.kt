package com.ariesmercado.gweather

import com.ariesmercado.gweather.data.repository.GWeatherRepositoryImpl
import com.ariesmercado.gweather.data.source.local.dao.GWeatherDao
import com.ariesmercado.gweather.data.source.local.database.AppDatabase
import com.ariesmercado.gweather.data.source.local.entity.WeatherEntity
import com.ariesmercado.gweather.data.source.remote.api.GWeatherApi
import com.ariesmercado.gweather.data.source.remote.model.Main
import com.ariesmercado.gweather.data.source.remote.model.Sys
import com.ariesmercado.gweather.data.source.remote.model.Weather
import com.ariesmercado.gweather.data.source.remote.model.WeatherResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class GWeatherRepositoryImplTest {

    @Mock private lateinit var api: GWeatherApi
    @Mock private lateinit var database: AppDatabase
    @Mock private lateinit var dao: GWeatherDao
    @Mock private lateinit var auth: FirebaseAuth
    @Mock private lateinit var mockUser: FirebaseUser

    private lateinit var repository: GWeatherRepositoryImpl

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        `when`(database.gWeatherDao()).thenReturn(dao)
        repository = GWeatherRepositoryImpl(api, database, auth)
    }

    @Test
    fun `getCurrentWeather returns WeatherResponse and saves to DB`() = runTest {
        // Arrange
        val lat = 14.6
        val lon = 120.9
        val uid = "user123"

        val weatherResponse = WeatherResponse(
            dt = 1234567890,
            name = "Manila",
            sys = Sys(country = "PH"),
            main = Main(temp = 303.15),
            weather = listOf(Weather(description = "sunny", icon = "01d"))
        )

        `when`(api.getCurrentWeather(lat, lon)).thenReturn(weatherResponse)
        `when`(auth.currentUser).thenReturn(mockUser)
        `when`(mockUser.uid).thenReturn(uid)

        // Act
        val result = repository.getCurrentWeather(lat, lon)

        // Assert
        assertEquals(weatherResponse, result)
        verify(api).getCurrentWeather(lat, lon)
        verify(dao).insert(
            WeatherEntity(
                dt = 1234567890,
                name = "Manila",
                country = "PH",
                temp = 303.15,
                description = "sunny",
                icon = "01d",
                uuid = uid
            )
        )
    }

    @Test(expected = Exception::class)
    fun `getCurrentWeather throws exception when API fails`() = runTest {
        val lat = 0.0
        val lon = 0.0
        `when`(api.getCurrentWeather(lat, lon)).thenThrow(RuntimeException("Network error"))

        repository.getCurrentWeather(lat, lon)
    }

    @Test
    fun `getWeatherHistory returns flow from DAO`() = runTest {
        // Arrange
        val uid = "user123"
        val mockList = listOf(
            WeatherEntity(1, "Manila", "PH", 303.15, "cloudy", "02d", uid)
        )

        `when`(auth.currentUser).thenReturn(mockUser)
        `when`(mockUser.uid).thenReturn(uid)
        `when`(dao.allUserHistory(uid)).thenReturn(flowOf(mockList))

        // Act
        val emissions = repository.getWeatherHistory().first() // ✅ Collect first emission from Flow

        // Assert
        assertEquals(1, emissions.size)
        assertEquals("Manila", emissions.first().name)
        verify(dao).allUserHistory(uid)
    }

    @Test
    fun `currentUserUid returns Firebase user uid`() {
        val uid = "abc123"
        `when`(auth.currentUser).thenReturn(mockUser)
        `when`(mockUser.uid).thenReturn(uid)

        val result = repository.currentUserUid()

        assertEquals(uid, result)
    }

    @Test
    fun `currentUserUid returns null when no user logged in`() {
        `when`(auth.currentUser).thenReturn(null)

        val result = repository.currentUserUid()

        assertNull(result)
    }
}
