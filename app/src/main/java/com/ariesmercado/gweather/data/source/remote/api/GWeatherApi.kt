package com.ariesmercado.gweather.data.source.remote.api

import com.ariesmercado.gweather.BuildConfig
import com.ariesmercado.gweather.data.source.remote.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface GWeatherApi {

    @Headers("Content-Type: application/json; charset=utf-8")
    @GET("data/2.5/weather")
    suspend fun getCurrentWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String = BuildConfig.API_KEY,
    ): WeatherResponse
}