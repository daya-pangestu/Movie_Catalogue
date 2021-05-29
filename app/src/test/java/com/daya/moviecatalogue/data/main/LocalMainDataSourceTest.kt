package com.daya.moviecatalogue.data.main

import androidx.paging.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.daya.moviecatalogue.data.main.movie.Movie
import com.daya.moviecatalogue.data.main.movie.local.MovieDao
import com.daya.moviecatalogue.data.main.movie.local.MovieEntity
import com.daya.moviecatalogue.data.main.tvshow.local.TvShowDao
import com.daya.moviecatalogue.data.main.tvshow.local.TvShowEntity
import com.daya.moviecatalogue.mapToMovieEntity
import com.daya.moviecatalogue.mapToTvShowEntity
import com.daya.moviecatalogue.shared.*
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import javax.inject.Inject

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class LocalMainDataSourceTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    lateinit var movieDao: MovieDao

    lateinit var tvshowDao: TvShowDao

    lateinit var localMainDataSource: LocalMainDataSource

    @Before
    fun setUp() {
        movieDao = mock()
        tvshowDao = mock()
        localMainDataSource = LocalMainDataSource(movieDao, tvshowDao)
    }


    @Test
    fun `getListMovies called movieDao#getMoviesPaged when invoked`() = runBlocking(mainCoroutineRule.testDispatcher) {
        whenever(movieDao.getMoviesPaged()).thenReturn(dummyPagingMovies)

        val actual = localMainDataSource
            .getListMovies()

        verify(movieDao).getMoviesPaged()
        assertThat(actual).isEqualTo(dummyPagingMovies)
    }

    @Test
    fun `getListTvShow called tvShowDao#getTvShows when invoked`() = mainCoroutineRule.testDispatcher.runBlockingTest {
        whenever(tvshowDao.getTvShowsPaged()).thenReturn(dummyPagingTvShows)

        val actual = localMainDataSource
            .getListTvShow()

        verify(tvshowDao).getTvShowsPaged()
        assertThat(actual).isEqualTo(dummyPagingTvShows)
    }

    private val dummyPagingMovies : PagingSource<Int,MovieEntity> = object : PagingSource<Int, MovieEntity>() {
        override fun getRefreshKey(state: PagingState<Int, MovieEntity>) : Int? = null
        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieEntity> {
            return LoadResult.Page(
                nextKey = null,
                prevKey = null,
                data = DataDummy.getListMovie().mapListMovieToMovieEntity()
            )
        }
    }

    private val dummyPagingTvShows : PagingSource<Int,TvShowEntity> = object : PagingSource<Int, TvShowEntity>() {
        override fun getRefreshKey(state: PagingState<Int, TvShowEntity>) : Int? = null
        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TvShowEntity> {
            return LoadResult.Page(
                nextKey = null,
                prevKey = null,
                data = DataDummy.getListTvShow().mapListTvShowToTvShowEntity()
            )
        }
    }
}