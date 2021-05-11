package com.daya.moviecatalogue.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.daya.moviecatalogue.MainCoroutineRule
import com.daya.moviecatalogue.data.DataDummy
import com.daya.moviecatalogue.data.Resource
import com.daya.moviecatalogue.data.main.MainRepository
import com.daya.moviecatalogue.data.main.movie.Movie
import com.daya.moviecatalogue.data.main.tvshow.TvShow
import com.daya.moviecatalogue.getOrAwaitValue
import com.daya.moviecatalogue.observeForTesting
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
@ExperimentalCoroutinesApi
class DetailViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val dummyMovie : Movie = DataDummy.getListMovie()[1]
    private val dummyTvShow : TvShow = DataDummy.getListTvShow()[7]

    private lateinit var viewModel: DetailViewModel
    private lateinit var repository : MainRepository

    @Before
    fun setUp() {
        repository = mock()
        viewModel = DetailViewModel(repository)
    }

    @Test
    fun `observeMovie should return movie based by id`() = mainCoroutineRule.testDispatcher.runBlockingTest {
        whenever(repository.getDetailMovie(dummyMovie.id)).thenReturn(dummyMovie)
        viewModel.submitMovie(dummyMovie.id)

        //initial value
        assertThat(viewModel.observeMovie().getOrAwaitValue()).isEqualTo(Resource.Loading)

        //latest value
        viewModel.observeMovie().observeForTesting {
            assertThat(viewModel.observeMovie().getOrAwaitValue()).isEqualTo(Resource.Loading)
        }
    }

    @Test
    fun `observeTvShow should return tvShow based by id`() = mainCoroutineRule.testDispatcher.runBlockingTest {
        whenever(repository.getDetailTvShow(dummyTvShow.id)).thenReturn(dummyTvShow)
        viewModel.submitTvShow(dummyTvShow.id)

        //initial value
        assertThat(viewModel.observeTvShow().getOrAwaitValue()).isEqualTo(Resource.Loading)

        //latest value
        viewModel.observeTvShow().observeForTesting {
            assertThat(viewModel.observeTvShow().getOrAwaitValue()).isEqualTo(Resource.Loading)
        }
    }
}