package com.daya.moviecatalogue.di

import com.daya.moviecatalogue.BuildConfig
import com.daya.moviecatalogue.data.main.movie.response.DetailMovieResponse
import com.daya.moviecatalogue.data.main.movie.response.MovieResponse
import com.daya.moviecatalogue.data.main.tvshow.response.DetailTvShowResponse
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
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(addApiInterceptor : OkHttpClient, moshi : Moshi, httpUrl: HttpUrl) :Retrofit =
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
                .addQueryParameter("api_key",BuildConfig.MOVIE_KEY)
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
    fun provideMovieDbApi(retrofit: Retrofit) : TheMovieDbApi =
        retrofit.create(TheMovieDbApi::class.java)

}

interface TheMovieDbApi{
    @GET("discover/tv")
    fun discoverTvShow() : Call<TvShowResponse>

    @GET("discover/tv")
    suspend fun discoverTvShowCorooutine(
        @Query("page")page :Int
    ) : TvShowResponse

    @GET("discover/movie")
    fun discoverMovie(): Call<MovieResponse>

    @GET("discover/movie")
    suspend fun discoverMovieCoroutine(
        @Query("page")page :Int
    ) : MovieResponse

    @GET("movie/{movie_id}")
    fun getDetailMovie(
            @Path("movie_id") movie_Id : Int
    ) : Call<DetailMovieResponse>

    @GET("tv/{tv_id}")
    fun getDetailTvShow(
            @Path("tv_id") tv_id : Int
    ) : Call<DetailTvShowResponse>

}