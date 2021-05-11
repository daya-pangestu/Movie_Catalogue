package com.daya.moviecatalogue.data.main

import com.daya.moviecatalogue.data.main.movie.response.DetailMovie
import com.daya.moviecatalogue.data.main.tvshow.response.DetailTvShow
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

class RemoteDetailDataSourceTest {
    val mockWebServer = MockWebServer()
    val moshi: Moshi = Moshi.Builder().build()
    val api: TheMovieDbApi = Retrofit.Builder()
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
    fun `get detail movie should return DetailMovie`() {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(Fake.detailMovie)
        )

        val jsonAdapter = moshi.adapter(DetailMovie::class.java)
        val expected = jsonAdapter.fromJson(Fake.detailMovie)
        assertThat(expected).isNotNull()

        val movieId = expected!!.id

        runBlocking {
            val actual = remoteDetailDataSource.getDetailMovie(movieId)
            assertThat(actual).isEqualTo(expected)
        }
    }

    @Test
    fun `get detail tvShow should return DetailTvShow`() {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(Fake.detalTvShow)
        )

        val jsonAdapter = moshi.adapter(DetailTvShow::class.java)
        val expected = jsonAdapter.fromJson(Fake.detalTvShow)
        assertThat(expected).isNotNull()

        val tvShowId = expected!!.id

        runBlocking {
            val actual = remoteDetailDataSource.getDetailTvShow(tvShowId)
            assertThat(actual).isEqualTo(expected)
        }
    }
}