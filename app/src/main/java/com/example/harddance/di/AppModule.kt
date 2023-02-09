package com.example.harddance.di

import android.content.Context
import com.example.harddance.data.repository.HearthisRepository
import com.example.harddance.player.service.MusicConnector
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesMusicConnector(
        @ApplicationContext context: Context
    ) = MusicConnector(context)

    @Provides
    @Singleton
    fun providesRepository() = HearthisRepository()
}