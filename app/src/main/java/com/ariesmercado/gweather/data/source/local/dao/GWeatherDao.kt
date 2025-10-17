package com.ariesmercado.gweather.data.source.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.ariesmercado.gweather.common.BaseDao
import com.ariesmercado.gweather.data.source.local.entity.WeatherEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface GWeatherDao : BaseDao<WeatherEntity> {
    @Query("SELECT * FROM $TABLE_NAME WHERE uuid = :id")
    fun allUserHistory(id: String): Flow<List<WeatherEntity>>

    companion object {
        const val TABLE_NAME = "WeatherHistory"
    }
}