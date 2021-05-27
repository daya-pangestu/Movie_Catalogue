package com.daya.moviecatalogue.data.main

import androidx.paging.PagingSource
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
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class LocalMainDataSourceTest {

    @get:Rule
    var hiltAndroidRule = HiltAndroidRule(this)

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Inject
    lateinit var movieDao: MovieDao

    @Inject
    lateinit var tvshowDao: TvShowDao

    lateinit var localMainDataSource: LocalMainDataSource

    @Before
    fun setUp() {
        hiltAndroidRule.inject()
        localMainDataSource = LocalMainDataSource(movieDao, tvshowDao)
    }

    private val dummyFlowMovies =
        flow<List<MovieEntity>> { DataDummy.getListMovie().map { it.mapToMovieEntity() } }

    private val dummyFlowTvShows =
        flow<List<TvShowEntity>> { DataDummy.getListTvShow().map { it.mapToTvShowEntity() } }

    //need updated
    @Test
    fun `verify localMainDataSource#getListMovies called movieDao#getMovies`() = runBlocking(mainCoroutineRule.testDispatcher) {
//        whenever(movieDao.getMovies()).thenReturn(dummyFlowMovies)
//        val actual = localMainDataSource.getListMovies()
//
//        assertThat(actual).isEqualTo(dummyFlowMovies)
    }

    @Test
    fun `verify localMainDataSource#getListTvShow called tvShowDao#getTvShows`() = mainCoroutineRule.testDispatcher.runBlockingTest {
        whenever(tvshowDao.getTvShows()).thenReturn(dummyFlowTvShows)
        val actual = localMainDataSource.getListTvShow()
        assertThat(actual).isEqualTo(dummyFlowTvShows)
    }

}