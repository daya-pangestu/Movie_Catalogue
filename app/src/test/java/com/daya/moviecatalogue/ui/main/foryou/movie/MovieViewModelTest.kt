package com.daya.moviecatalogue.ui.main.foryou.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.daya.moviecatalogue.data.main.RemoteMainRepository
import com.daya.moviecatalogue.shared.DataDummy
import com.daya.moviecatalogue.shared.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.mock

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class MovieViewModelTest{

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var remoteMainRepository: RemoteMainRepository
    private lateinit var movieViewModel: MovieViewModel

    private val dummyListMovies = DataDummy.getListMovie()

    @Before
    fun setUp() {
        remoteMainRepository = mock()
        movieViewModel = MovieViewModel(remoteMainRepository)
    }

    @Test
    fun `movieViewModel#discoverMovie should return resSucces listMovies`() = mainCoroutineRule.testDispatcher.runBlockingTest{
//        whenever(mainRepository.discoverMovies()).thenReturn(dummyListMovies)
//
//        //initial value
//        val actualResLoading = movieViewModel.discoverMovie.getOrAwaitValue()
//        assertThat(actualResLoading).isEqualTo(Resource.Loading)
//
//        //latest value
//        movieViewModel.discoverMovie.observeForTesting {
//            assertThat(movieViewModel.discoverMovie.value).isEqualTo(Resource.Success(dummyListMovies))
//        }
//        verify(mainRepository).discoverMovies()
    }
}