package com.ariesmercado.gweather.domain.usecase

import com.ariesmercado.gweather.common.constant.Constants
import com.ariesmercado.gweather.domain.repository.AuthRepository
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(email: String, password: String): Flow<AuthResult> = flow {
        val data = repository.signIn(email, password)
        emit(data)
    }.catch { e ->
        if (e.message?.contains(Constants.ERROR_INVALID_CREDENTIAL) == true) {
            throw Exception(Constants.ERROR_INVALID_EMAIL_OR_PASSWORD)
        } else {
            throw e
        }
    }
}