package com.ariesmercado.gweather.presenter.weather.current

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import com.ariesmercado.gweather.R
import com.ariesmercado.gweather.common.Resource
import com.ariesmercado.gweather.common.constant.Constants
import com.ariesmercado.gweather.data.source.remote.model.WeatherResponse
import com.ariesmercado.gweather.presenter.weather.WeatherViewModel
import com.ariesmercado.gweather.presenter.weather.components.ErrorView
import com.ariesmercado.gweather.presenter.weather.components.LoadingView
import com.ariesmercado.gweather.presenter.weather.components.formatUnixTime
import com.project.sampleapptheming.ui.theme.mainTheme
import java.util.Locale

@Composable
fun CurrentWeatherScreen(
    modifier: Modifier = Modifier,
    viewModel: WeatherViewModel = hiltViewModel()
) {
    val state by viewModel.weatherState.collectAsState()

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.onPrimaryContainer
    ) {
        when (state) {
            is Resource.Loading -> LoadingView()
            is Resource.Error -> ErrorView()
            else -> state.data?.let { WeatherContent(it) } ?: ErrorView()
        }
    }
}

@Composable
private fun WeatherContent(data: WeatherResponse) {
    val city = data.name ?: stringResource(R.string.unknown_city)
    val country = data.sys?.country?.let { Locale("", it).displayCountry } ?: stringResource(R.string.unknown_country)
    val temperatureCelsius = data.main?.temp?.let {
        val celsius = it - 273.15
        stringResource(R.string.temperature_format, celsius)
    } ?: Constants.NOT_AVAILABLE

    val formattedDateTime = formatUnixTime(data.dt, "EEEE, MMM dd, yyyy • hh:mm a")
    val sunrise = formatUnixTime(data.sys?.sunrise, "hh:mm a")
    val sunset = formatUnixTime(data.sys?.sunset, "hh:mm a")

    val weatherCondition = data.weather?.firstOrNull()?.description
        ?.replaceFirstChar { it.uppercase() } ?: stringResource(R.string.no_description)
    val iconCode = data.weather?.firstOrNull()?.icon
    val imageUrl = stringResource(R.string.https_openweathermap_org_img_wn_2x_png, iconCode.orEmpty())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        WeatherHeader(formattedDateTime)
        LocationInfo(city, country)
        WeatherImage(imageUrl, weatherCondition)
        TemperatureInfo(temperatureCelsius, weatherCondition)
        SunriseSunsetBox(sunrise, sunset)
    }
}

@Composable
private fun WeatherHeader(formattedDateTime: String) {
    Text(
        text = stringResource(R.string.current_weather),
        style = MaterialTheme.typography.headlineMedium.copy(
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        ),
        textAlign = TextAlign.Center
    )
    Spacer(modifier = Modifier.height(6.dp))
    Text(
        text = formattedDateTime,
        style = MaterialTheme.typography.bodyMedium.copy(
            color = MaterialTheme.colorScheme.primary
        ),
        textAlign = TextAlign.Center
    )
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
private fun LocationInfo(city: String, country: String) {
    Text(
        text = "$city, $country",
        style = MaterialTheme.typography.titleMedium.copy(
            color = MaterialTheme.colorScheme.primary
        ),
        textAlign = TextAlign.Center
    )
}

@Composable
private fun WeatherImage(imageUrl: String, description: String) {
    SubcomposeAsyncImage(
        model = imageUrl,
        contentDescription = description,
        modifier = Modifier.size(200.dp),
        loading = {
            Box(modifier = Modifier.size(200.dp), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        },
        error = {
            Box(modifier = Modifier.size(200.dp), contentAlignment = Alignment.Center) {
                Text(stringResource(R.string.image_not_available))
            }
        }
    )
}

@Composable
private fun TemperatureInfo(temp: String, condition: String) {
    Text(
        text = temp,
        style = MaterialTheme.typography.displayMedium.copy(
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
    )
    Text(
        text = condition,
        style = MaterialTheme.typography.titleLarge.copy(
            color = MaterialTheme.colorScheme.onSurface
        )
    )
    Spacer(modifier = Modifier.height(24.dp))
}

@Composable
private fun SunriseSunsetBox(sunrise: String, sunset: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(mainTheme)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        SunriseSunsetItem(stringResource(R.string.sunrise), sunrise)
        SunriseSunsetItem(stringResource(R.string.sunset), sunset)
    }
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
private fun SunriseSunsetItem(label: String, time: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(label, style = MaterialTheme.typography.labelLarge)
        Text(time, style = MaterialTheme.typography.bodyMedium)
    }
}