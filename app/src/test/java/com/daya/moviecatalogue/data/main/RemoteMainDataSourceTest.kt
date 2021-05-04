package com.daya.moviecatalogue.data.main

import androidx.test.espresso.IdlingRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.daya.moviecatalogue.data.main.movie.response.MovieResponse
import com.daya.moviecatalogue.di.TheMovieDbApi
import com.daya.moviecatalogue.di.idlingresource.DebugIdlingRes
import com.daya.moviecatalogue.di.idlingresource.DebugIdlingResource
import com.daya.moviecatalogue.di.idlingresource.IdlingResources
import com.daya.moviecatalogue.fake.Fake
import com.google.common.truth.Truth.assertThat
import com.squareup.moshi.Moshi
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
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
    fun `get movie should return MovieResponse`() {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(Fake.movieResponseSuccesfull)
        )

        val jsonAdapter = moshi.adapter(MovieResponse::class.java)
        val expected = jsonAdapter.fromJson(Fake.movieResponseSuccesfull)

        runBlocking {
            val actual = remoteMainDataSource.getListMovies()
            assertThat(actual.results.size).isAtLeast(5)

            assertThat(actual).isEqualTo(expected)
        }
    }
}


