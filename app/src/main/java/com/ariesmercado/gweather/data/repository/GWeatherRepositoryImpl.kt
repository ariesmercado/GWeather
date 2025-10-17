package com.ariesmercado.gweather.data.repository

import com.ariesmercado.gweather.data.source.local.database.AppDatabase
import com.ariesmercado.gweather.data.source.local.entity.WeatherEntity
import com.ariesmercado.gweather.data.source.remote.api.GWeatherApi
import com.ariesmercado.gweather.data.source.remote.model.WeatherResponse
import com.ariesmercado.gweather.domain.repository.GWeatherRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow


class GWeatherRepositoryImpl(
    private val api: GWeatherApi,
    private val database: AppDatabase,
    private val auth: FirebaseAuth
) : GWeatherRepository {

    override suspend fun getCurrentWeather(
        lat: Double,
        lon: Double
    ): WeatherResponse {
        try {
            return api.getCurrentWeather(lat, lon).also {
                database.gWeatherDao().insert(
                    WeatherEntity(
                        it.dt,
                        it.name,
                        it.sys?.country,
                        it.main?.temp,
                        it.weather?.firstOrNull()?.description,
                        it.weather?.firstOrNull()?.icon,
                        currentUserUid()
                    )
                )
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override fun getWeatherHistory(): Flow<List<WeatherEntity>> =
        database.gWeatherDao().allUserHistory(currentUserUid().orEmpty())

    override fun currentUserUid(): String? = auth.currentUser?.uid

}