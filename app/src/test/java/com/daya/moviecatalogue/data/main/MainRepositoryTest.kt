package com.daya.moviecatalogue.data.main

import com.daya.moviecatalogue.data.main.movie.response.DetailMovie
import com.daya.moviecatalogue.data.main.movie.response.MovieResponse
import com.daya.moviecatalogue.data.main.tvshow.response.DetailTvShow
import com.daya.moviecatalogue.data.main.tvshow.response.TvShowResponse
import com.daya.moviecatalogue.fake.Fake
import com.google.common.truth.Truth.assertThat
import com.squareup.moshi.Moshi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class MainRepositoryTest {

    private val mainDataSource: RemoteMainDataSource = mock()
    private val detailDataSource : RemoteDetailDataSource = mock()
    lateinit var mainRepository :MainRepository

    private val moshi = Moshi.Builder().build()

    @Before
    fun setUp() {
        mainRepository = MainRepository(detailDataSource,mainDataSource)
    }

    @Test
    fun `discoverMovie should return MovieResponse`() {
        val movieJsonString = Fake.discoverMovieSuccesfull
        val adapter = moshi.adapter(MovieResponse::class.java)
        val movieResponse = adapter.fromJson(movieJsonString)
        runBlocking {
            whenever(mainDataSource.getListMovies()).thenReturn(movieResponse)
            val actualListMovie = mainRepository.discoverMovies()
            verify(mainDataSource).getListMovies()

            assertThat(actualListMovie).isNotNull()
            assertThat(actualListMovie.results.size).isAtLeast(5)
        }
    }

    @Test
    fun `discoverTvShow should return TvShowResponse`() {
        val tvShowJsonString = Fake.discoverTvShowSuccesFul
        val adapter = moshi.adapter(TvShowResponse::class.java)
        val expectedTvShowResponse = adapter.fromJson(tvShowJsonString)
        runBlocking {
            whenever(mainDataSource.getListTvShow()).thenReturn(expectedTvShowResponse)
            val listTvShow = mainRepository.discoverTvShow()
            verify(mainDataSource).getListTvShow()

            assertThat(listTvShow).isNotNull()
            assertThat(listTvShow.results.size).isAtLeast(5)
        }
    }

    @Test
    fun `getDetailMovie should return DetailMovie`() {
        val movieId = 460465 // mortal kombat
        val adapter = moshi.adapter(DetailMovie::class.java)
        val detailMovieJsonString = Fake.detailMovie
        val expectedDetailMovie = adapter.fromJson(detailMovieJsonString)

        runBlocking {
            whenever(detailDataSource.getDetailMovie(movieId)).thenReturn(expectedDetailMovie)
            val actualDetailMovie = mainRepository.getDetailMovie(movieId)

            verify(detailDataSource).getDetailMovie(movieId)
            assertThat(actualDetailMovie).isNotNull()

        }
    }

    @Test
    fun `getDetailTvShow should return DetailTvShow`() {
        val movieId = 88396 //the falcon and winter soldier
        val adapter = moshi.adapter(DetailTvShow::class.java)
        val detailTvShowJsonString = Fake.detalTvShow
        val expectedDetailTvShow = adapter.fromJson(detailTvShowJsonString)

        runBlocking {
            whenever(detailDataSource.getDetailTvShow(movieId)).thenReturn(expectedDetailTvShow)
            val actualDetailTvShow = mainRepository.getDetailTvShow(movieId)

            verify(detailDataSource).getDetailTvShow(movieId)
            assertThat(actualDetailTvShow).isNotNull()

        }
    }
}