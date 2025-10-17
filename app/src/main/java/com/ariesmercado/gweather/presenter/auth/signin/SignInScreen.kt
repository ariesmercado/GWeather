package com.ariesmercado.gweather.presenter.auth.signin

import android.content.Intent
import android.util.Patterns
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.ariesmercado.gweather.R
import com.ariesmercado.gweather.common.constant.Constants.PASSWORD_MIN_LIMIT
import com.ariesmercado.gweather.presenter.auth.AuthScreens
import com.ariesmercado.gweather.presenter.auth.AuthViewModel
import com.ariesmercado.gweather.presenter.auth.state.AuthUiState
import com.ariesmercado.gweather.presenter.weather.WeatherActivity
import com.project.sampleapptheming.ui.theme.mainTheme

@Composable
fun SignInScreen(
    navController: NavHostController,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var showEmailError by remember { mutableStateOf(false) }
    var showPasswordError by remember { mutableStateOf(false) }

    LaunchedEffect(state) {
        when (state) {
            is AuthUiState.Success -> {
                val intent = Intent(context, WeatherActivity::class.java)
                context.startActivity(intent)
            }

            is AuthUiState.Error -> {
                Toast.makeText(
                    context,
                    (state as AuthUiState.Error).message ?: context.getString(R.string.error),
                    Toast.LENGTH_SHORT
                ).show()
            }

            else -> {}
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = mainTheme
            )
            .padding(24.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                stringResource(R.string.sign_in),
                fontSize = 28.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = email,
                onValueChange = {
                    showEmailError = false
                    email = it
                },
                label = { Text(stringResource(R.string.email)) },
                singleLine = true,
                isError = showEmailError,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            AnimatedVisibility(visible = showEmailError) {
                Text(
                    stringResource(R.string.invalid_email_format),
                    color = Color.Red,
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = {
                    showPasswordError = false
                    password = it
                },
                label = { Text("Password") },
                singleLine = true,
                isError = showPasswordError,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            AnimatedVisibility(visible = showPasswordError) {
                Text(
                    stringResource(R.string.password_must_be_at_least_6_characters),
                    color = Color.Red,
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    showEmailError = !Patterns.EMAIL_ADDRESS.matcher(email).matches()
                    showPasswordError = password.length < PASSWORD_MIN_LIMIT

                    if (!showEmailError && !showPasswordError) {
                        viewModel.signIn(email, password)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                if (state == AuthUiState.Loading) {
                    CircularProgressIndicator(color = mainTheme)
                } else {
                    Text(
                        stringResource(R.string.sign_in),
                        style = MaterialTheme.typography.bodyLarge,
                        color = mainTheme
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            TextButton(onClick = { navController.navigate(AuthScreens.Register.tag) }) {
                Text(
                    stringResource(R.string.don_t_have_an_account_sign_up),
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}