package com.daya.moviecatalogue.ui.main.favorite.movie

import androidx.paging.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.daya.moviecatalogue.data.main.LocalPersistRepository
import com.daya.moviecatalogue.data.main.movie.Movie
import com.daya.moviecatalogue.data.main.movie.local.MovieDao
import com.daya.moviecatalogue.mapToMovieEntity
import com.daya.moviecatalogue.movieDiffCallback
import com.daya.moviecatalogue.shared.DataDummy
import com.daya.moviecatalogue.shared.GetDiffer
import com.daya.moviecatalogue.shared.MainCoroutineRule
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import javax.inject.Inject
//use an actual database, so this test placed in androidTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class MovieFavViewModelTests {

    @get:Rule(order = 0)
    var hiltAndroidRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var mainCoroutineRule = MainCoroutineRule()

    @Inject
    lateinit var movieDao :MovieDao

    @Inject
    lateinit var LocalPersistRepository : LocalPersistRepository
    lateinit var  movieFavFavViewModel : MovieFavViewModel
    private val dummyListMovies = DataDummy.getListMovie().map { it.mapToMovieEntity() }

    @Before
    fun setUp() {
        hiltAndroidRule.inject()
        movieFavFavViewModel = MovieFavViewModel(LocalPersistRepository)
    }

    @Test
    fun `favoriteMovies_get_pagingData`() =
        //dont use runBlokingTest yet, see
        runBlocking(mainCoroutineRule.testDispatcher) {
            movieDao.batchInsertMovie(dummyListMovies)

            val differ = GetDiffer<Movie>(
                difffCallback = movieDiffCallback,
                mainDispatcer = mainCoroutineRule.testDispatcher,
                workerDispatcher = mainCoroutineRule.testDispatcher
            ).invoke()

            val job = launch {
                movieFavFavViewModel.favoriteMovies.collectLatest {
                    differ.submitData(it)
                }
            }
            assertThat(differ.snapshot().items.size).isEqualTo(dummyListMovies.size)
            job.cancel()
        }
}