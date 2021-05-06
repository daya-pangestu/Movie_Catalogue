package com.daya.moviecatalogue.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.daya.moviecatalogue.MainCoroutineRule
import com.daya.moviecatalogue.data.DataDummy
import com.daya.moviecatalogue.data.Resource
import com.daya.moviecatalogue.data.main.MainRepository
import com.daya.moviecatalogue.data.main.RemoteDetailDataSource
import com.daya.moviecatalogue.data.main.RemoteMainDataSource
import com.daya.moviecatalogue.di.TheMovieDbApi
import com.daya.moviecatalogue.getOrAwaitValue
import com.daya.moviecatalogue.observeForTesting
import com.google.common.truth.Truth.assertThat
import com.jraska.livedata.test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
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
        viewModel = MainViewModel(mainRepository,mainCoroutineRule.testDispatcher)
    }

    @Test
    fun `getMovie should not null`() {
        val resMovies = viewModel.discoverMovie.getOrAwaitValue()
        assertThat(resMovies).isNotNull()
    }

    @Test
    fun `getMovie should not empty`() {
        mainCoroutineRule.testDispatcher.runBlockingTest {
            assertThat(viewModel.discoverMovie.value).isEqualTo(Resource.Loading)
            delay(300)
            assertThat(viewModel.discoverMovie.value).isEqualTo(Resource.Success(null))
        }
    }

    @Test
    fun `getTvShow should not null`() {
        val listTvShow = viewModel.getMovie
        assertThat(listTvShow).isNotNull()
    }

    @Test
    fun `getTvShow should not empty`() {
        val listMovie = viewModel.getMovie
        assertThat(listMovie).isNotEmpty()
    }

}