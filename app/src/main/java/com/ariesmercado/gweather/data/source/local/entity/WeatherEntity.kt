package com.ariesmercado.gweather.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ariesmercado.gweather.data.source.local.dao.GWeatherDao

@Entity(tableName = GWeatherDao.TABLE_NAME)
data class WeatherEntity(
    @PrimaryKey
    val dt: Long? = null,
    val name: String? = null,
    val country: String? = null,
    val temp: Double? = null,
    val description: String? = null,
    val icon: String? = null,
    val uuid: String? = null
)