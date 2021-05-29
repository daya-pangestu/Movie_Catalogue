package com.daya.moviecatalogue.data.main

import androidx.paging.PagingSource
import com.daya.moviecatalogue.data.main.movie.response.DetailMovieResponse
import com.daya.moviecatalogue.data.main.tvshow.response.DetailTvShowResponse
import com.daya.moviecatalogue.shared.Fake
import com.daya.moviecatalogue.mapToMovie
import com.daya.moviecatalogue.maptoTvShow
import com.daya.moviecatalogue.shared.data.FakeRemoteMainDataSource
import com.google.common.truth.Truth.assertThat
import com.squareup.moshi.Moshi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class RemoteMainRepositoryTest {

    lateinit var remoteDetailDataSource: RemoteDetailDataSource

    lateinit var mainDataSource: MainDataSource<PagingSource<Int, DetailMovieResponse>, PagingSource<Int, DetailTvShowResponse>>

    lateinit var remoteMainRepository: RemoteMainRepository

    val moshi: Moshi = Moshi.Builder().build()

    @Before
    fun setUp() {
        remoteDetailDataSource = mock()
        mainDataSource = mock()
        remoteMainRepository = RemoteMainRepository(remoteDetailDataSource,mainDataSource)
    }

    @Test
    fun `discoverMovie should return flowPagingData`(){
        runBlocking{
            val fakeMainDataSource = FakeRemoteMainDataSource()
            val expectedPagingSource = fakeMainDataSource.getListMovies()
            whenever(mainDataSource.getListMovies()).thenReturn(expectedPagingSource)
            val actual = remoteMainRepository.discoverMovies()
            assertThat(actual).isNotNull()
        }
    }

    @Test
    fun `discoverTvShow should return flowPagingData`() {
        runBlocking{
            val fakeMainDataSource = FakeRemoteMainDataSource()
            val expectedPagingSource = fakeMainDataSource.getListTvShow()
            whenever(mainDataSource.getListTvShow()).thenReturn(expectedPagingSource)
            val actual = remoteMainRepository.discoverTvShow()
            assertThat(actual).isNotNull()
        }
    }

    @Test
    fun `getDetailMovie should return DetailMovie`() {
        val movieId = 460465 // mortal kombat
        val adapter = moshi.adapter(DetailMovieResponse::class.java)
        val detailMovieJsonString = Fake.detailMovie
        val expectedDetailMovie = adapter.fromJson(detailMovieJsonString)

        val expectedMovie = expectedDetailMovie?.mapToMovie()
        assertThat(expectedMovie).isNotNull()

        runBlocking {
            whenever(remoteDetailDataSource.getDetailMovie(movieId)).thenReturn(expectedDetailMovie)
            val actualDetailMovie = remoteMainRepository.getDetailMovie(movieId)

            verify(remoteDetailDataSource).getDetailMovie(movieId)
            assertThat(actualDetailMovie).isNotNull()
            assertThat(actualDetailMovie).isEqualTo(expectedMovie)
        }
    }

    @Test
    fun `getDetailTvShow should return DetailTvShow`() {
        val movieId = 88396 //the falcon and winter soldier
        val adapter = moshi.adapter(DetailTvShowResponse::class.java)
        val detailTvShowJsonString = Fake.detalTvShow
        val expectedDetailTvShow = adapter.fromJson(detailTvShowJsonString)
        val expectedTvShow = expectedDetailTvShow?.maptoTvShow()
        assertThat(expectedTvShow).isNotNull()

        runBlocking {
            whenever(remoteDetailDataSource.getDetailTvShow(movieId)).thenReturn(expectedDetailTvShow)
            val actualDetailTvShow = remoteMainRepository.getDetailTvShow(movieId)

            verify(remoteDetailDataSource).getDetailTvShow(movieId)
            assertThat(actualDetailTvShow).isNotNull()
            assertThat(actualDetailTvShow).isEqualTo(expectedTvShow)

        }
    }
}