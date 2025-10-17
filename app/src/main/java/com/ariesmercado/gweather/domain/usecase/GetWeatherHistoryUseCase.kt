package com.ariesmercado.gweather.domain.usecase

import com.ariesmercado.gweather.data.source.local.entity.WeatherEntity
import com.ariesmercado.gweather.domain.repository.GWeatherRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWeatherHistoryUseCase @Inject constructor(private val repository: GWeatherRepository) {
    operator fun invoke(): Flow<List<WeatherEntity>> {
        return repository.getWeatherHistory()
    }
}