package com.daya.moviecatalogue.data.main

import androidx.paging.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.daya.moviecatalogue.data.main.movie.Movie
import com.daya.moviecatalogue.shared.DataDummy
import com.daya.moviecatalogue.data.main.movie.local.MovieDao
import com.daya.moviecatalogue.data.main.movie.local.MovieEntity
import com.daya.moviecatalogue.data.main.tvshow.local.TvShowDao
import com.daya.moviecatalogue.data.main.tvshow.local.TvShowEntity
import com.daya.moviecatalogue.mapToMovieEntity
import com.daya.moviecatalogue.mapToTvShowEntity
import com.daya.moviecatalogue.shared.MainCoroutineRule
import com.daya.moviecatalogue.shared.mapListMovieToMovieEntity
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

    private val dummyFlowTvShows =
        flow<List<TvShowEntity>> { DataDummy.getListTvShow().map { it.mapToTvShowEntity() } }

    @Test
    fun `getListMovies called movieDao#getMoviesPaged`() = runBlocking(mainCoroutineRule.testDispatcher) {
        whenever(movieDao.getMoviesPaged()).thenReturn(dummyPagingMovies)

        val actual = localMainDataSource
            .getListMovies()

        verify(movieDao).getMoviesPaged()
        assertThat(actual).isEqualTo(dummyPagingMovies)
    }

    @Test
    fun `verify localMainDataSource#getListTvShow called tvShowDao#getTvShows`() = mainCoroutineRule.testDispatcher.runBlockingTest {
        whenever(tvshowDao.getTvShows()).thenReturn(dummyFlowTvShows)
        val actual = localMainDataSource.getListTvShow()
        assertThat(actual).isEqualTo(dummyFlowTvShows)

        //TODO made tvshow test just same as above
    }

}