package com.ariesmercado.gweather.domain.usecase

import com.ariesmercado.gweather.domain.repository.AuthRepository
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(email: String, password: String): Flow<AuthResult> = flow {
        val data = repository.signUp(email, password)
        emit(data)
    }
}