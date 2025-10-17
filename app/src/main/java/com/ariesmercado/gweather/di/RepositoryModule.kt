package com.ariesmercado.gweather.di

import com.ariesmercado.gweather.data.repository.AuthRepositoryImpl
import com.ariesmercado.gweather.data.repository.GWeatherRepositoryImpl
import com.ariesmercado.gweather.data.source.local.database.AppDatabase
import com.ariesmercado.gweather.data.source.remote.api.GWeatherApi
import com.ariesmercado.gweather.domain.repository.AuthRepository
import com.ariesmercado.gweather.domain.repository.GWeatherRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideGWeatherRepository(
        api: GWeatherApi,
        database: AppDatabase,
        firebaseAuth: FirebaseAuth
    ): GWeatherRepository {
        return GWeatherRepositoryImpl(api, database, firebaseAuth)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        firebaseAuth: FirebaseAuth
    ): AuthRepository {
        return AuthRepositoryImpl(firebaseAuth)
    }
}