package com.ariesmercado.gweather.presenter.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ariesmercado.gweather.common.constant.Constants
import com.ariesmercado.gweather.domain.usecase.RegisterUseCase
import com.ariesmercado.gweather.domain.usecase.SignInUseCase
import com.ariesmercado.gweather.presenter.auth.state.AuthUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val uiState: StateFlow<AuthUiState> = _uiState

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            try {
                signInUseCase(email.trim(), password).collect {
                    _uiState.value = AuthUiState.Success
                }
            } catch (e: Exception) {
                _uiState.value = AuthUiState.Error(e.message ?: Constants.ERROR_SIGN_IN_FAILED)
            }
        }
    }

    fun signUp(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            try {
                registerUseCase(email.trim(), password).collect {
                    _uiState.value = AuthUiState.Success
                }
            } catch (e: Exception) {
                _uiState.value = AuthUiState.Error(e.message ?: Constants.ERROR_SIGN_UP_FAILED)
            }
        }
    }
}