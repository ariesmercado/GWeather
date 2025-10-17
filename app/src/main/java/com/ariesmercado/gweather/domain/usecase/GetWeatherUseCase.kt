package com.ariesmercado.gweather.domain.usecase

import com.ariesmercado.gweather.common.Resource
import com.ariesmercado.gweather.common.constant.Constants
import com.ariesmercado.gweather.data.source.remote.model.WeatherResponse
import com.ariesmercado.gweather.domain.repository.GWeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(
    private val repository: GWeatherRepository
) {
    operator fun invoke(lat: Double, lon: Double): Flow<Resource<WeatherResponse>> = flow {
        emit(Resource.Loading())
        val data = repository.getCurrentWeather(lat, lon)
        emit(Resource.Success(data))
    }.catch { _ ->
        emit(Resource.Error(Constants.ERROR_SEARCH))
    }
}