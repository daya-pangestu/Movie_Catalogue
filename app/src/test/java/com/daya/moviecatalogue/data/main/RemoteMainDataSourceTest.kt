package com.daya.moviecatalogue.data.main

import androidx.paging.PagingSource
import com.daya.moviecatalogue.data.main.movie.response.DetailMovieResponse
import com.daya.moviecatalogue.data.main.movie.response.MovieResponse
import com.daya.moviecatalogue.data.main.tvshow.response.TvShowResponse
import com.daya.moviecatalogue.di.TheMovieDbApi
import com.daya.moviecatalogue.fake.Fake
import com.daya.moviecatalogue.shared.MainCoroutineRule
import com.google.common.truth.Truth.assertThat
import com.squareup.moshi.Moshi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@ExperimentalCoroutinesApi
class RemoteMainDataSourceTest {
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

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    val remoteMainDataSource = RemoteMainDataSource(api)

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `discoverMovie should return MovieResponse`() {
        runBlocking(mainCoroutineRule.testDispatcher) {
            mockWebServer.enqueue(
                MockResponse()
                    .setResponseCode(200)
                    .setBody(Fake.discoverMovieSuccesfull)
            )

            val jsonAdapter = moshi.adapter(MovieResponse::class.java)
            val movieResponse = jsonAdapter.fromJson(Fake.discoverMovieSuccesfull)

            val expected = PagingSource.LoadResult.Page<Int, DetailMovieResponse>(
                data = movieResponse!!.results,
                prevKey = null,
                nextKey = null
            )

            val actualPagingSource = remoteMainDataSource.getListMovies()
            val actual = actualPagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = movieResponse.results.size,
                    placeholdersEnabled = false
                )
            )

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


