package com.ariesmercado.gweather.presenter.auth.state

sealed class AuthUiState {
    data object Idle : AuthUiState()
    data object Loading : AuthUiState()
    data object Success : AuthUiState()
    data class Error(val message: String?) : AuthUiState()
}