package com.ariesmercado.gweather.presenter.auth.register

import android.content.Intent
import android.util.Patterns
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
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
fun RegisterScreen(
    navController: NavHostController,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var showEmailError by remember { mutableStateOf(false) }
    var showPasswordError by remember { mutableStateOf(false) }
    var showConfirmPasswordError by remember { mutableStateOf(false) }

    HandleAuthState(state, context)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = mainTheme)
            .padding(24.dp)
    ) {
        Column(verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxSize()) {
            HeaderTitle()
            Spacer(Modifier.height(24.dp))

            EmailField(email, showEmailError) {
                email = it
                showEmailError = false
            }

            Spacer(Modifier.height(16.dp))

            PasswordField(
                value = password,
                label = stringResource(R.string.password),
                showError = showPasswordError,
                errorMessage = stringResource(R.string.password_must_be_at_least_6_characters)
            ) {
                password = it
                showPasswordError = false
            }

            Spacer(Modifier.height(16.dp))

            PasswordField(
                value = confirmPassword,
                label = stringResource(R.string.confirm_password),
                showError = showConfirmPasswordError,
                errorMessage = stringResource(R.string.passwords_do_not_match)
            ) {
                confirmPassword = it
                showConfirmPasswordError = false
            }

            Spacer(Modifier.height(24.dp))

            RegisterButton(
                state = state,
                email = email,
                password = password,
                confirmPassword = confirmPassword,
                onValidate = { e, p, c ->
                    showEmailError = e
                    showPasswordError = p
                    showConfirmPasswordError = c
                },
                onRegister = { viewModel.signUp(email, password) }
            )

            Spacer(Modifier.height(12.dp))

            SignInRedirect(navController)

            Spacer(Modifier.height(12.dp))
        }
    }
}

@Composable
private fun HandleAuthState(state: AuthUiState, context: android.content.Context) {
    LaunchedEffect(state) {
        when (state) {
            is AuthUiState.Success -> {
                val intent = Intent(context, WeatherActivity::class.java)
                context.startActivity(intent)
            }

            is AuthUiState.Error -> {
                Toast.makeText(
                    context,
                    state.message ?: context.getString(R.string.error),
                    Toast.LENGTH_SHORT
                ).show()
            }

            else -> {}
        }
    }
}

@Composable
private fun HeaderTitle() {
    Text(
        stringResource(R.string.create_account),
        fontSize = 28.sp,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}

@Composable
private fun EmailField(value: String, showError: Boolean, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(stringResource(R.string.email)) },
        singleLine = true,
        isError = showError,
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
    )

    AnimatedVisibility(visible = showError) {
        Text(stringResource(R.string.invalid_email_format), color = Color.Red, fontSize = 12.sp)
    }
}

@Composable
private fun PasswordField(
    value: String,
    label: String,
    showError: Boolean,
    errorMessage: String,
    onValueChange: (String) -> Unit
) {
    var visible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = true,
        isError = showError,
        visualTransformation = if (visible) VisualTransformation.None else PasswordVisualTransformation(),
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
    )

    AnimatedVisibility(visible = showError) {
        Text(errorMessage, color = Color.Red, fontSize = 12.sp)
    }
}

@Composable
private fun RegisterButton(
    state: AuthUiState,
    email: String,
    password: String,
    confirmPassword: String,
    onValidate: (emailError: Boolean, passwordError: Boolean, confirmError: Boolean) -> Unit,
    onRegister: () -> Unit
) {
    Button(
        onClick = {
            val emailError = !Patterns.EMAIL_ADDRESS.matcher(email).matches()
            val passwordError = password.length < PASSWORD_MIN_LIMIT
            val confirmError = confirmPassword != password

            onValidate(emailError, passwordError, confirmError)

            if (!emailError && !passwordError && !confirmError) {
                onRegister()
            }
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        if (state == AuthUiState.Loading) {
            CircularProgressIndicator(color = mainTheme)
        } else {
            Text(
                stringResource(R.string.create_account),
                style = MaterialTheme.typography.bodyLarge,
                color = mainTheme
            )
        }
    }
}

@Composable
private fun SignInRedirect(navController: NavHostController) {
    TextButton(onClick = { navController.navigate(AuthScreens.Register.tag) }) {
        Text(
            stringResource(R.string.already_have_an_account_sign_in),
            color = Color.White,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}
