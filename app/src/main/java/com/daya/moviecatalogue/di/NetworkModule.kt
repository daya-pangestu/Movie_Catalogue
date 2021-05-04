package com.daya.moviecatalogue.di

import com.daya.moviecatalogue.data.main.movie.response.MovieResponse
import com.daya.moviecatalogue.data.main.tvshow.response.TvShowResponse
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(addApiInterceptor : OkHttpClient, moshi : Moshi, httpUrl: HttpUrl) =
        Retrofit.Builder()
            .baseUrl(httpUrl)
            .client(addApiInterceptor)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

    @Provides
    @Singleton
    fun providesHttpUrl() =
            HttpUrl.get("https://api.themoviedb.org/3/")

    @Provides
    @Singleton
    fun provideApiInterceptor(): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
        okHttpClient.addInterceptor {
            val original = it.request()
            val originalHttpUrl = original.url()

            val urlWithApiKey = originalHttpUrl.newBuilder()
                .addQueryParameter("api_key","5e4eb4432423f10cc7577597c5cc41e8")
                .build()

            val requestBuilder = original.newBuilder()
                .url(urlWithApiKey)
            val request = requestBuilder.build()

            it.proceed(request)
        }
        return okHttpClient.build()
    }

    @Provides
    @Singleton
    fun provideMovieDbApi(retrofit: Retrofit) =
        retrofit.create(TheMovieDbApi::class.java)

}

interface TheMovieDbApi{
    @GET("discover/tv")
    fun discoverTvShow() : Call<TvShowResponse>

    @GET("discover/tv")
    fun discoverMovie() : Call<MovieResponse>

}