package com.ariesmercado.gweather.presenter.auth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ariesmercado.gweather.presenter.auth.register.RegisterScreen
import com.ariesmercado.gweather.presenter.auth.signin.SignInScreen
import com.ariesmercado.gweather.presenter.ui.theme.GWeatherAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GWeatherAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = AuthScreens.SignIn.tag
                    ) {
                        composable(AuthScreens.SignIn.tag) { SignInScreen(navController) }
                        composable(AuthScreens.Register.tag) { RegisterScreen(navController) }
                    }
                }
            }
        }
    }
}
