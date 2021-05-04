package com.daya.moviecatalogue.data.main

import com.daya.moviecatalogue.data.main.movie.response.MovieResponse
import com.daya.moviecatalogue.data.main.tvshow.response.TvShowResponse
import com.daya.moviecatalogue.di.TheMovieDbApi
import com.daya.moviecatalogue.fake.Fake
import com.google.common.truth.Truth.assertThat
import com.squareup.moshi.Moshi
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RemoteMainDataSourceTest {
    val mockWebServer = MockWebServer()
    val moshi =Moshi.Builder().build()
    val api = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .addConverterFactory(
            MoshiConverterFactory.create(
                moshi
            )
        )
        .build()
        .create(TheMovieDbApi::class.java)

    val remoteMainDataSource =  RemoteMainDataSource(api)

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
    @Test
    fun `discoverMovie should return MovieResponse`() {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(Fake.discoverMovieSuccesfull)
        )

        val jsonAdapter = moshi.adapter(MovieResponse::class.java)
        val expected = jsonAdapter.fromJson(Fake.discoverMovieSuccesfull)

        runBlocking {
            val actual = remoteMainDataSource.getListMovies()
            assertThat(actual.results.size).isAtLeast(5)

            assertThat(actual).isEqualTo(expected)
        }
    }

    @Test
    fun `discoverTvshow should return TvShowResponse`() {
        mockWebServer.enqueue(
                MockResponse()
                        .setResponseCode(200)
                        .setBody(Fake.discoverTvShowSuccesFul)
        )

        val jsonAdapter = moshi.adapter(TvShowResponse::class.java)
        val expected = jsonAdapter.fromJson(Fake.discoverTvShowSuccesFul)

        runBlocking {
            val actual = remoteMainDataSource.getListTvShow()
            assertThat(actual.results.size).isAtLeast(5)

            assertThat(actual).isEqualTo(expected)
        }
    }
}


