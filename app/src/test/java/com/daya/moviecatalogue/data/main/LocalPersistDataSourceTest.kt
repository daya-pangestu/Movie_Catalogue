package com.daya.moviecatalogue.data.main

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.daya.moviecatalogue.data.DataDummy
import com.daya.moviecatalogue.data.db.MovieCatDatabase
import com.daya.moviecatalogue.data.main.movie.local.MovieDao
import com.daya.moviecatalogue.data.main.tvshow.local.TvShowDao
import com.daya.moviecatalogue.mapToMovieEntity
import com.daya.moviecatalogue.mapToTvShowEntity
import com.daya.moviecatalogue.shared.MainCoroutineRule
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class LocalPersistDataSourceTest{
   
    private val movieDao : MovieDao = mock()
    private val tvShowDao : TvShowDao = mock()
    private lateinit var localPersistDataSource: LocalPersistDataSource
    
    private val dummyListMovie = DataDummy.getListMovie()
    private val dummyListTvShow = DataDummy.getListTvShow()
    private val dummyMovie = dummyListMovie[4]
    private val dumyTvShow = dummyListTvShow[5]

    private val dummyListMovieEntity = dummyListMovie.map { it.mapToMovieEntity() }
    private val dummyListTvShowEntity = dummyListTvShow.map { it.mapToTvShowEntity() }
    private val dummyMovieEntity = dummyListMovieEntity[4]
    private val dumyTvShowEntity = dummyListTvShowEntity[5]

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()
    
    @Before
    fun setUp() {
        localPersistDataSource = LocalPersistDataSource(movieDao,tvShowDao)
    }


    @Test
    fun `verify localPersistDataSource#addMovieToFavorite called movieDao#insertMovie`() = mainCoroutineRule.testDispatcher.runBlockingTest {
        whenever(movieDao.insertMovie(dummyMovieEntity)).thenReturn(dummyMovie.id.toLong())

        localPersistDataSource.addMovieToFavorite(dummyMovieEntity)

        verify(movieDao).insertMovie(dummyMovieEntity)
    }

    @Test
    fun `verify localPersistDataSource#addTvShowToFavorte called tvShowDao#insertTvShow`() = mainCoroutineRule.testDispatcher.runBlockingTest {
        whenever(tvShowDao.insertTvShow(dumyTvShowEntity)).thenReturn(dumyTvShowEntity.id.toLong())

        localPersistDataSource.addTvShowToFavorte(dumyTvShowEntity)

        verify(tvShowDao).insertTvShow(dumyTvShowEntity)
    }

    @Test
    fun `verify localPersistDataSource#deleteMovieFromFavorite called movieDao#deleteMovie`() = mainCoroutineRule.testDispatcher.runBlockingTest {
        whenever(movieDao.deleteMovie(dummyMovieEntity)).thenReturn(1)

        localPersistDataSource.deleteMovieFromFavorite(dummyMovieEntity)

        verify(movieDao).deleteMovie(dummyMovieEntity)
    }

    @Test
    fun `verify localPersistDataSource#deleteTvShowFromFavorite called tvShowDao#deleteTvShow`() = mainCoroutineRule.testDispatcher.runBlockingTest {
        whenever(tvShowDao.deleteTvShow(dumyTvShowEntity)).thenReturn(1)

        localPersistDataSource.deleteTvShowFromFavorite(dumyTvShowEntity)

        verify(tvShowDao).deleteTvShow(dumyTvShowEntity)
    }















    
}