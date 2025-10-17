package com.ariesmercado.gweather.presenter.weather.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import com.ariesmercado.gweather.R
import com.ariesmercado.gweather.common.Resource
import com.ariesmercado.gweather.common.constant.Constants
import com.ariesmercado.gweather.data.source.local.entity.WeatherEntity
import com.ariesmercado.gweather.presenter.weather.WeatherViewModel
import com.ariesmercado.gweather.presenter.weather.components.ErrorView
import com.ariesmercado.gweather.presenter.weather.components.LoadingView
import com.ariesmercado.gweather.presenter.weather.components.formatUnixTime
import com.project.sampleapptheming.ui.theme.mainTheme
import java.util.Locale

@Composable
fun WeatherHistoryScreen(
    modifier: Modifier = Modifier,
    viewModel: WeatherViewModel = hiltViewModel()
) {
    val state by viewModel.weatherState.collectAsState()
    val history by viewModel.history.collectAsState()
    Surface(modifier = modifier.fillMaxSize(), color = MaterialTheme.colorScheme.onPrimaryContainer) {
        when (state) {
            is Resource.Loading -> LoadingView()
            is Resource.Error -> ErrorView()
            else -> state.data?.let { WeatherHistoryList(history.reversed()) } ?: ErrorView()
        }
    }
}

@Composable
fun WeatherHistoryList(
    history: List<WeatherEntity>
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(history) { data ->
            WeatherHistoryItem(data)
        }

        item {
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
private fun WeatherHistoryItem(data: WeatherEntity) {
    val city = data.name ?: stringResource(R.string.unknown_city)
    val country = data.country?.let { Locale("", it).displayCountry } ?: stringResource(R.string.unknown_country)
    val temperatureCelsius = data.temp?.let {
        val celsius = it - 273.15
        stringResource(R.string.temperature_format, celsius)
    } ?: Constants.NOT_AVAILABLE
    val formattedDateTime = formatUnixTime(data.dt, "EEE, MMM dd • hh:mm a")
    val weatherCondition = data.description
        ?.replaceFirstChar { it.uppercase() } ?: stringResource(R.string.no_description)
    val iconCode = data.icon
    val imageUrl = stringResource(R.string.https_openweathermap_org_img_wn_2x_png, iconCode.orEmpty())

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight() // ⬅️ Compact card height
            .clip(RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(
            containerColor = mainTheme // ✅ white card
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // 🌦️ Weather Icon (smaller)
            SubcomposeAsyncImage(
                model = imageUrl,
                contentDescription = weatherCondition,
                modifier = Modifier.size(70.dp),
                loading = {
                    Box(
                        modifier = Modifier.size(70.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(strokeWidth = 2.dp)
                    }
                },
                error = {
                    Box(
                        modifier = Modifier.size(70.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("N/A", style = MaterialTheme.typography.labelSmall)
                    }
                }
            )

            Spacer(modifier = Modifier.width(16.dp))

            // 📄 Info column
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "$city, $country",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.primary
                    )
                )

                Text(
                    text = weatherCondition,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = temperatureCelsius,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = formattedDateTime,
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = MaterialTheme.colorScheme.primary
                    )
                )
            }
        }
    }
}