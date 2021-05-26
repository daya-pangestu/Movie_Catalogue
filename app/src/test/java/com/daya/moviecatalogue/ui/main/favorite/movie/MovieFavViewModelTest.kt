package com.daya.moviecatalogue.ui.main.favorite.movie

import androidx.paging.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
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
import kotlinx.coroutines.delay
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
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class MovieFavViewModelTest {

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
    fun `verify`() =
        runBlocking(mainCoroutineRule.testDispatcher) {
            val insertedRowIds = movieDao.batchInsertMovie(dummyListMovies)
            assertThat(insertedRowIds).isEqualTo(dummyListMovies.map { it.id.toLong() })

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
            delay(3000)
            assertThat(differ.snapshot().items.size).isEqualTo(dummyListMovies.size)
            job.cancel()
        }
}