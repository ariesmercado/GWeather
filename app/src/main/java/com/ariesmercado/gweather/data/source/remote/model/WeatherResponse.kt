package com.ariesmercado.gweather.data.source.remote.model

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("coord") val coord: Coord? = null,
    @SerializedName("weather") val weather: List<Weather>? = null,
    @SerializedName("base") val base: String? = null,
    @SerializedName("main") val main: Main? = null,
    @SerializedName("visibility") val visibility: Int? = null,
    @SerializedName("wind") val wind: Wind? = null,
    @SerializedName("clouds") val clouds: Clouds? = null,
    @SerializedName("dt") val dt: Long? = null,
    @SerializedName("sys") val sys: Sys? = null,
    @SerializedName("timezone") val timezone: Int? = null,
    @SerializedName("id") val id: Int? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("cod") val cod: Int? = null
)

data class Coord(
    @SerializedName("lon") val lon: Double? = null,
    @SerializedName("lat") val lat: Double? = null
)

data class Weather(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("main") val main: String? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("icon") val icon: String? = null
)

data class Main(
    @SerializedName("temp") val temp: Double? = null,
    @SerializedName("feels_like") val feelsLike: Double? = null,
    @SerializedName("temp_min") val tempMin: Double? = null,
    @SerializedName("temp_max") val tempMax: Double? = null,
    @SerializedName("pressure") val pressure: Int? = null,
    @SerializedName("humidity") val humidity: Int? = null,
    @SerializedName("sea_level") val seaLevel: Int? = null,
    @SerializedName("grnd_level") val grndLevel: Int? = null
)

data class Wind(
    @SerializedName("speed") val speed: Double? = null,
    @SerializedName("deg") val deg: Int? = null,
    @SerializedName("gust") val gust: Double? = null
)

data class Clouds(
    @SerializedName("all") val all: Int? = null
)

data class Sys(
    @SerializedName("type") val type: Int? = null,
    @SerializedName("id") val id: Int? = null,
    @SerializedName("country") val country: String? = null,
    @SerializedName("sunrise") val sunrise: Long? = null,
    @SerializedName("sunset") val sunset: Long? = null
)

