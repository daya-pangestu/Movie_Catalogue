package com.daya.moviecatalogue.ui.main.favorite.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.daya.moviecatalogue.data.LocalPersistRepository
import com.daya.moviecatalogue.data.main.movie.Movie
import com.daya.moviecatalogue.data.main.movie.local.MovieDao
import com.daya.moviecatalogue.mapToMovieEntity
import com.daya.moviecatalogue.shared.DataDummy
import com.daya.moviecatalogue.shared.MainCoroutineRule
import com.daya.moviecatalogue.shared.getOrAwaitValue
import com.daya.moviecatalogue.shared.observeForTesting
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.internal.toImmutableList
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class MovieFavViewModelTest {

    @get:Rule(order = 1)
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule(order = 2)
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var movieFavViewModel: MovieFavViewModel

    private lateinit var localPersistRepository: LocalPersistRepository

    private val dummyListMovies = DataDummy.getListMovie()
    private val dummyFlowMovies = flowOf(dummyListMovies)

    @Before
    fun setUp() {
        localPersistRepository = mock()
        movieFavViewModel = MovieFavViewModel(localPersistRepository)
    }

    @Test
    fun `verify localPersistRepository#getAllFavoriteMovies get called when favoriteMovies invoked`() = mainCoroutineRule.testDispatcher.runBlockingTest{
        whenever(localPersistRepository.getAllFavoriteMovies()).thenReturn(dummyFlowMovies)

        val actual = movieFavViewModel.favoriteMovies.getOrAwaitValue()

        verify(localPersistRepository).getAllFavoriteMovies()
        assertThat(actual).isEqualTo(dummyListMovies)
    }
}