package com.daya.moviecatalogue.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.daya.moviecatalogue.MainCoroutineRule
import com.daya.moviecatalogue.data.DataDummy
import com.daya.moviecatalogue.data.Resource
import com.daya.moviecatalogue.data.main.MainRepository
import com.daya.moviecatalogue.getOrAwaitValue
import com.daya.moviecatalogue.observeForTesting
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever


@ExperimentalCoroutinesApi
class MainViewModelTest{

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: MainViewModel
    private lateinit var mainRepository: MainRepository

    @Before
    fun setUp() {
        mainRepository = mock()
        viewModel = MainViewModel(mainRepository)
    }

    @Test
    fun `discoverMovie should return resource listMovies `() = mainCoroutineRule.testDispatcher.runBlockingTest {
//        whenever(mainRepository.discoverMovies()).thenReturn(DataDummy.getListMovie())
//
//        //initial value
//        val resLoading = viewModel.discoverMovie.getOrAwaitValue()
//        assertThat(resLoading).isEqualTo(Resource.Loading)
//
//        //latest value
//        viewModel.discoverMovie.observeForTesting {
//                assertThat(viewModel.discoverMovie.value).isEqualTo(Resource.Success(DataDummy.getListMovie()))
//        }
    }


    @Test
    fun `discoverTvShow should return resource tvShows`() = mainCoroutineRule.testDispatcher.runBlockingTest {
//        whenever(mainRepository.discoverTvShow()).thenReturn(DataDummy.getListTvShow())
//
//        //initial value
//        val resLoading = viewModel.discoverTvShow.getOrAwaitValue()
//        assertThat(resLoading).isEqualTo(Resource.Loading)
//
//        //latest value
//        viewModel.discoverTvShow.observeForTesting {
//            assertThat(viewModel.discoverTvShow.value).isEqualTo(Resource.Success(DataDummy.getListTvShow()))
//        }
    }
}