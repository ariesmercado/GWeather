package com.ariesmercado.gweather.presenter.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ariesmercado.gweather.common.Resource
import com.ariesmercado.gweather.data.source.local.entity.WeatherEntity
import com.ariesmercado.gweather.data.source.remote.model.WeatherResponse
import com.ariesmercado.gweather.domain.usecase.GetWeatherHistoryUseCase
import com.ariesmercado.gweather.domain.usecase.GetWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val getWeatherHistoryUseCase: GetWeatherHistoryUseCase
) : ViewModel() {

    private val _weatherState =
        MutableStateFlow<Resource<WeatherResponse>>(Resource.Loading())
    val weatherState = _weatherState.asStateFlow()

    val history: StateFlow<List<WeatherEntity>> = getWeatherHistoryUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    fun loadWeather(lat: Double, lon: Double) {
        viewModelScope.launch {
            getWeatherUseCase(lat, lon).collect { result ->
                _weatherState.value = result
            }
        }
    }
}