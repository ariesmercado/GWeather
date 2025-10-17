package com.ariesmercado.gweather.presenter.weather.components

import com.ariesmercado.gweather.common.constant.Constants.NOT_AVAILABLE
import com.ariesmercado.gweather.common.constant.Constants.TIME_MILLISECONDS
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formatUnixTime(timestamp: Long?, pattern: String): String {
    return if (timestamp != null) {
        val date = Date(timestamp * TIME_MILLISECONDS)
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        sdf.format(date)
    } else NOT_AVAILABLE
}