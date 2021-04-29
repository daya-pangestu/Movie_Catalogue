package com.daya.moviecatalogue.di

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun ProvideMoshi() =
        Moshi.Builder()
            .build()


    @Provides
    @Singleton
    fun provideRetrofit(moshi : Moshi) =
        Retrofit.Builder()
            .baseUrl("http:something")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
}