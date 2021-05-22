package com.daya.moviecatalogue.data.main

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.daya.moviecatalogue.shared.DataDummy
import com.daya.moviecatalogue.data.main.movie.local.MovieDao
import com.daya.moviecatalogue.data.main.movie.local.MovieEntity
import com.daya.moviecatalogue.data.main.tvshow.local.TvShowDao
import com.daya.moviecatalogue.data.main.tvshow.local.TvShowEntity
import com.daya.moviecatalogue.mapToMovieEntity
import com.daya.moviecatalogue.mapToTvShowEntity
import com.daya.moviecatalogue.shared.MainCoroutineRule
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class LocalMainDataSourceTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val movieDao: MovieDao = mock()
    private val tvshowDao: TvShowDao = mock()
    lateinit var localMainDataSource: LocalMainDataSource

    @Before
    fun setUp() {
        localMainDataSource = LocalMainDataSource(movieDao,tvshowDao)
    }

    private val dummyFlowMovies =
        flow<List<MovieEntity>> { DataDummy.getListMovie().map { it.mapToMovieEntity() } }

    private val dummyFlowTvShows =
        flow<List<TvShowEntity>> { DataDummy.getListTvShow().map { it.mapToTvShowEntity() } }

    @Test
    fun `verify localMainDataSource#getListMovies called movieDao#getMovies`() = mainCoroutineRule.testDispatcher.runBlockingTest {
        whenever(movieDao.getMovies()).thenReturn(dummyFlowMovies)
        val actual = localMainDataSource.getListMovies()
        assertThat(actual).isEqualTo(dummyFlowMovies)
    }

    @Test
    fun `verify localMainDataSource#getListTvShow called tvShowDao#getTvShows`() = mainCoroutineRule.testDispatcher.runBlockingTest {
        whenever(tvshowDao.getTvShows()).thenReturn(dummyFlowTvShows)
        val actual = localMainDataSource.getListTvShow()
        assertThat(actual).isEqualTo(dummyFlowTvShows)
    }

}