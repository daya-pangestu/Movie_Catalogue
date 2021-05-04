package com.daya.moviecatalogue.data.main

import com.daya.moviecatalogue.data.main.movie.Movie
import com.daya.moviecatalogue.data.main.movie.response.DetailMovie
import com.daya.moviecatalogue.data.main.movie.response.MovieResponse
import com.daya.moviecatalogue.di.TheMovieDbApi
import com.daya.moviecatalogue.fake.Fake
import com.google.common.truth.Truth
import com.squareup.moshi.Moshi
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RemoteDetailDataSourceTest {
    val mockWebServer = MockWebServer()
    val moshi = Moshi.Builder().build()
    val api = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(
                    MoshiConverterFactory.create(
                            moshi
                    )
            )
            .build()
            .create(TheMovieDbApi::class.java)

    val remoteDetailDataSource =  RemoteDetailDataSource(api)

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `get detail movie should return Movie`() {

    }
}