package com.ariesmercado.gweather.data.source.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ariesmercado.gweather.data.source.local.dao.GWeatherDao
import com.ariesmercado.gweather.data.source.local.entity.WeatherEntity

const val VERSION_NUMBER = 1

@Database(
    entities = [WeatherEntity::class],
    version = VERSION_NUMBER,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun gWeatherDao(): GWeatherDao
}