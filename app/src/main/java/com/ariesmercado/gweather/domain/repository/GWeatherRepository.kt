package com.ariesmercado.gweather.domain.repository

import com.ariesmercado.gweather.data.source.local.entity.WeatherEntity
import com.ariesmercado.gweather.data.source.remote.model.WeatherResponse
import kotlinx.coroutines.flow.Flow

interface GWeatherRepository {
    suspend fun getCurrentWeather(
        lat: Double,
        lon: Double
    ): WeatherResponse

    fun currentUserUid(): String?
    fun getWeatherHistory(): Flow<List<WeatherEntity>>
}