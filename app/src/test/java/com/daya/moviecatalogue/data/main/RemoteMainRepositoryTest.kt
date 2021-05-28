package com.daya.moviecatalogue.data.main

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import com.daya.moviecatalogue.data.main.movie.response.DetailMovieResponse
import com.daya.moviecatalogue.data.main.tvshow.response.TvShowResponse
import com.daya.moviecatalogue.shared.DataDummy
import com.daya.moviecatalogue.shared.data.FakeRemoteMainDataSource
import com.daya.moviecatalogue.shared.mapListMovieToDetailMovieResponse
import com.google.common.truth.Truth.assertThat
import com.squareup.moshi.Moshi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import javax.inject.Inject

class RemoteMainRepositoryTest {

    lateinit var mainRepository: RemoteMainRepository

    lateinit var remoteDetailDataSource: RemoteDetailDataSource

    val mainDataSource : MainDataSource<PagingSource<Int, DetailMovieResponse>,TvShowResponse> = mock()

    val fakeMainDataSource = FakeRemoteMainDataSource()

    @Inject
    lateinit var moshi : Moshi

    @Before
    fun setUp() {
        remoteDetailDataSource = mock()

        mainRepository = RemoteMainRepository(remoteDetailDataSource,mainDataSource)
    }

    @Test
    fun `discoverMovie should call mainDataSource#getListMovies when invoked`() {
        val expectedPagingSource = fakeMainDataSource.getListMovies()
        val expectedFlowPagingData = Pager(config = PagingConfig(pageSize = 20)){
            expectedPagingSource
        }.flow
        runBlocking {
            whenever(mainDataSource.getListMovies()).thenReturn(expectedPagingSource)

            val actualFlowPagingData = mainRepository.discoverMovies()
            verify(mainDataSource).getListMovies()
            assertThat(actualFlowPagingData).isEqualTo(expectedFlowPagingData)
        }
    }

//    @Test
//    fun `discoverTvShow should return TvShowResponse`() {
//        val tvShowJsonString = Fake.discoverTvShowSuccesFul
//        val adapter = moshi.adapter(TvShowResponse::class.java)
//        val expectedTvShowResponse = adapter.fromJson(tvShowJsonString)
//
//        val expectedTvShow = expectedTvShowResponse?.results?.map { it.maptoTvShow() }
//        assertThat(expectedTvShow).isNotNull()
//
//        runBlocking {
//            whenever(mainDataSource.getListTvShow()).thenReturn(expectedTvShowResponse)
//            val listTvShow = remoteMainRepository.discoverTvShow()
//            verify(mainDataSource).getListTvShow()
//
//            assertThat(listTvShow).isNotNull()
//            assertThat(listTvShow.size).isAtLeast(5)
//            assertThat(listTvShow).isEqualTo(expectedTvShow)
//        }
//    }

//    @Test
//    fun `getDetailMovie should return DetailMovie`() {
//        val movieId = 460465 // mortal kombat
//        val adapter = moshi.adapter(DetailMovieResponse::class.java)
//        val detailMovieJsonString = Fake.detailMovie
//        val expectedDetailMovie = adapter.fromJson(detailMovieJsonString)
//
//        val expectedMovie = expectedDetailMovie?.mapToMovie()
//        assertThat(expectedMovie).isNotNull()
//
//        runBlocking {
//            whenever(detailDataSource.getDetailMovie(movieId)).thenReturn(expectedDetailMovie)
//            val actualDetailMovie = remoteMainRepository.getDetailMovie(movieId)
//
//            verify(detailDataSource).getDetailMovie(movieId)
//            assertThat(actualDetailMovie).isNotNull()
//            assertThat(actualDetailMovie).isEqualTo(expectedMovie)
//        }
//    }
//
//    @Test
//    fun `getDetailTvShow should return DetailTvShow`() {
//        val movieId = 88396 //the falcon and winter soldier
//        val adapter = moshi.adapter(DetailTvShowResponse::class.java)
//        val detailTvShowJsonString = Fake.detalTvShow
//        val expectedDetailTvShow = adapter.fromJson(detailTvShowJsonString)
//        val expectedTvShow = expectedDetailTvShow?.maptoTvShow()
//        assertThat(expectedTvShow).isNotNull()
//
//        runBlocking {
//            whenever(detailDataSource.getDetailTvShow(movieId)).thenReturn(expectedDetailTvShow)
//            val actualDetailTvShow = remoteMainRepository.getDetailTvShow(movieId)
//
//            verify(detailDataSource).getDetailTvShow(movieId)
//            assertThat(actualDetailTvShow).isNotNull()
//            assertThat(actualDetailTvShow).isEqualTo(expectedTvShow)
//
//        }
//    }
}