package com.daya.moviecatalogue.di

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ReaderModule {
    @Provides
    @Singleton
    fun provideMoshi() : Moshi =
            Moshi.Builder()
                    .build()
}