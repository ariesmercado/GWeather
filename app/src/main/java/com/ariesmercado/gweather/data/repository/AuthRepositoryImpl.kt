package com.ariesmercado.gweather.data.repository

import com.ariesmercado.gweather.common.constant.Constants
import com.ariesmercado.gweather.domain.repository.AuthRepository
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class AuthRepositoryImpl(private val auth: FirebaseAuth) : AuthRepository {

    override suspend fun signIn(email: String, password: String): AuthResult =
        suspendCancellableCoroutine { cont ->
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        cont.resume(task.result!!)
                    } else {
                        cont.resumeWithException(task.exception ?: Exception(Constants.ERROR_UNKNOWN))
                    }
                }
        }

    override suspend fun signUp(email: String, password: String): AuthResult =
        suspendCancellableCoroutine { cont ->
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        cont.resume(task.result!!)
                    } else {
                        cont.resumeWithException(task.exception ?: Exception(Constants.ERROR_UNKNOWN))
                    }
                }
        }
}