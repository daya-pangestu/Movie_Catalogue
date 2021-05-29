package com.daya.moviecatalogue.ui.main.foryou.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.AsyncPagingDataDiffer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.daya.moviecatalogue.data.main.MainRepository
import com.daya.moviecatalogue.data.main.PersistRepository
import com.daya.moviecatalogue.data.main.RemoteMainRepository
import com.daya.moviecatalogue.data.main.movie.Movie
import com.daya.moviecatalogue.movieDiffCallback
import com.daya.moviecatalogue.shared.DataDummy
import com.daya.moviecatalogue.shared.MainCoroutineRule
import com.daya.moviecatalogue.shared.noopListCallback
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.mock
import javax.inject.Inject

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class MovieViewModelTest{

    @get:Rule(order = 0)
    var hiltAndroidRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var mainCoroutineRule = MainCoroutineRule()

    @Inject
    lateinit var remoteMainRepository: RemoteMainRepository
    private lateinit var movieViewModel: MovieViewModel

    private val dummyListMovies = DataDummy.getListMovie()

    @Before
    fun setUp() {
        hiltAndroidRule.inject()
        movieViewModel = MovieViewModel(remoteMainRepository)
    }

    @Test
    fun `movieViewModel#discoverMovie should return resSucces listMovies`() =
        runBlocking(mainCoroutineRule.testDispatcher){
            val differ = AsyncPagingDataDiffer<Movie>(
                diffCallback = movieDiffCallback,
                updateCallback = noopListCallback,
                mainDispatcher = mainCoroutineRule.testDispatcher,
                workerDispatcher = mainCoroutineRule.testDispatcher
            )

            val job = launch {
                movieViewModel.discoverMovie.collectLatest {
                    differ.submitData(it)
                }
            }
            assertThat(differ.snapshot()).isNotEmpty()
            job.cancel()
    }
}