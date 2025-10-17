package com.ariesmercado.gweather.presenter.weather

import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import com.ariesmercado.gweather.R
import com.ariesmercado.gweather.presenter.ui.theme.GWeatherAppTheme
import com.ariesmercado.gweather.presenter.weather.current.CurrentWeatherScreen
import com.ariesmercado.gweather.presenter.weather.history.WeatherHistoryScreen
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.project.sampleapptheming.ui.theme.mainTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherActivity: ComponentActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val viewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        loadWeather()
        setContent {
            GWeatherAppTheme {
                WeatherScreen()
            }
        }
    }

    // Get current location safely
    @SuppressLint("MissingPermission")
    private fun loadWeather() {
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            location?.let {
                val latitude = it.latitude
                val longitude = it.longitude
                viewModel.loadWeather(latitude,longitude)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreen() {
    var selectedItem by remember { mutableIntStateOf(0) }

    val items = WeatherScreens.entries
    val icons = listOf(R.drawable.baseline_cloud_24, Icons.Default.List)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Welcome to GWeather") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = mainTheme
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = mainTheme
            ) {
                items.forEachIndexed { index, label ->
                    NavigationBarItem(
                        icon = {
                            when (icons[index]) {
                                is Int -> {
                                    Icon(painterResource(icons[index] as Int) , contentDescription = label.tag)
                                }
                                else -> {
                                    Icon(icons[index] as ImageVector, contentDescription = label.tag)
                                }
                            }

                        },
                        label = { Text(label.tag) },
                        selected = selectedItem == index,
                        onClick = { selectedItem = index }
                    )
                }
            }
        }
    ) { innerPadding ->
        when (selectedItem) {
            0 -> CurrentWeatherScreen(Modifier.padding(innerPadding))
            1 -> WeatherHistoryScreen(Modifier.padding(innerPadding))
        }
    }
}

enum class WeatherScreens(val tag: String) {
    Current("Current"),
    History("History")
}



