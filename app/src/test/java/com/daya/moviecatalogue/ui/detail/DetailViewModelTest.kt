package com.daya.moviecatalogue.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.daya.moviecatalogue.shared.MainCoroutineRule
import com.daya.moviecatalogue.shared.DataDummy
import com.daya.moviecatalogue.data.main.LocalPersistRepository
import com.daya.moviecatalogue.data.Resource
import com.daya.moviecatalogue.data.main.RemoteMainRepository
import com.daya.moviecatalogue.data.main.movie.Movie
import com.daya.moviecatalogue.data.main.tvshow.TvShow
import com.daya.moviecatalogue.shared.getOrAwaitValue
import com.daya.moviecatalogue.shared.observeForTesting
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
    private lateinit var remoteMainRepository: RemoteMainRepository
    private lateinit var localPersistRepository: LocalPersistRepository

    @Before
    fun setUp() {
        remoteMainRepository = mock()
        localPersistRepository = mock()
        viewModel = DetailViewModel(remoteMainRepository,localPersistRepository)
    }

    @Test
    fun `observeMovie should return movie based by id`() = mainCoroutineRule.testDispatcher.runBlockingTest {
        whenever(remoteMainRepository.getDetailMovie(dummyMovie.id)).thenReturn(dummyMovie)
        viewModel.submitMovie(dummyMovie.id)

        //initial value
        assertThat(viewModel.observeMovie.getOrAwaitValue()).isEqualTo(Resource.Loading)

        //latest value
        viewModel.observeMovie.observeForTesting {
            assertThat(viewModel.observeMovie.value).isEqualTo(Resource.Success(dummyMovie))
        }
        verify(remoteMainRepository).getDetailMovie(dummyMovie.id)
    }

    @Test
    fun `observeTvShow should return tvShow based by id`() = mainCoroutineRule.testDispatcher.runBlockingTest {
        whenever(remoteMainRepository.getDetailTvShow(dummyTvShow.id)).thenReturn(dummyTvShow)
        viewModel.submitTvShow(dummyTvShow.id)

        //initial value
        assertThat(viewModel.observeTvShow.getOrAwaitValue()).isEqualTo(Resource.Loading)

        //latest value
        viewModel.observeTvShow.observeForTesting {
            assertThat(viewModel.observeTvShow.value).isEqualTo(Resource.Success(dummyTvShow))
        }
        verify(remoteMainRepository).getDetailTvShow(dummyTvShow.id)
    }

    @Test
    fun `observeIsFavorite should return true`() = mainCoroutineRule.testDispatcher.runBlockingTest {
        whenever(localPersistRepository.isFavorite(dummyMovie.id)).thenReturn(true)
        viewModel.startObserveIsFavorite(dummyMovie.id)

        //initial value
        assertThat(viewModel.checkIsFavorite.getOrAwaitValue()).isEqualTo(Resource.Loading)

        verify(localPersistRepository).isFavorite(dummyMovie.id)

        //latest value
        viewModel.checkIsFavorite.observeForTesting {
            assertThat(viewModel.checkIsFavorite.value).isEqualTo(Resource.Success(true))
        }
    }

    @Test
    fun `observeIsFavorite should return false`() = mainCoroutineRule.testDispatcher.runBlockingTest {
        whenever(localPersistRepository.isFavorite(dummyMovie.id)).thenReturn(false)
        viewModel.startObserveIsFavorite(dummyMovie.id)

        //initial value
        assertThat(viewModel.checkIsFavorite.getOrAwaitValue()).isEqualTo(Resource.Loading)

        verify(localPersistRepository).isFavorite(dummyMovie.id)

        //latest value
        viewModel.checkIsFavorite.observeForTesting {
            assertThat(viewModel.checkIsFavorite.value).isEqualTo(Resource.Success(false))
        }
    }

    @Test
    fun `addTofavorite should save movie`() = mainCoroutineRule.testDispatcher.runBlockingTest {
        //make sure movie is loaded
        whenever(remoteMainRepository.getDetailMovie(dummyMovie.id)).thenReturn(dummyMovie)
        viewModel.submitMovie(dummyMovie.id)

        viewModel.observeMovie.observeForTesting {
            assertThat(viewModel.observeMovie.value).isEqualTo(Resource.Success(dummyMovie))
        }

        whenever(localPersistRepository.addMovieToFavorite(dummyMovie)).thenReturn(dummyMovie.id.toLong())

        //call
        viewModel.addToFavorite()

        verify(localPersistRepository).addMovieToFavorite(dummyMovie)

        //assert when movie get saved, observeSaving has the rowId
        viewModel.observeSaving.observeForTesting {
            assertThat(viewModel.observeSaving.value).isEqualTo(Resource.Success(true))
        }
    }

    @Test
    fun `addTofavorite should save tvShow`() = mainCoroutineRule.testDispatcher.runBlockingTest {
        //make sure tvshow is loaded
        whenever(remoteMainRepository.getDetailTvShow(dummyTvShow.id)).thenReturn(dummyTvShow)
        viewModel.submitTvShow(dummyTvShow.id)

        viewModel.observeTvShow.observeForTesting {
            assertThat(viewModel.observeTvShow.value).isEqualTo(Resource.Success(dummyTvShow))
        }

        whenever(localPersistRepository.addTvShowToFavorite(dummyTvShow)).thenReturn(dummyTvShow.id.toLong())

        //call
        viewModel.addToFavorite()

        verify(localPersistRepository).addTvShowToFavorite(dummyTvShow)

        //assert when tvshow get saved, observeSaving has the rowId
        viewModel.observeSaving.observeForTesting {
            assertThat(viewModel.observeSaving.value).isEqualTo(Resource.Success(true))
        }
    }

    @Test
    fun `deleteFromFavorite should delete movie`() = mainCoroutineRule.testDispatcher.runBlockingTest {
        //make sure movie is loaded
        whenever(remoteMainRepository.getDetailMovie(dummyMovie.id)).thenReturn(dummyMovie)
        viewModel.submitMovie(dummyMovie.id)

        viewModel.observeMovie.observeForTesting {
            assertThat(viewModel.observeMovie.value).isEqualTo(Resource.Success(dummyMovie))
        }

        whenever(localPersistRepository.deleteMovieFromFavorite(dummyMovie)).thenReturn(1)

        //call
        viewModel.deleteFromFavorite()

        verify(localPersistRepository).deleteMovieFromFavorite(dummyMovie)

        //assert when movie get saved, observedeleting has the rowDeleted count
        viewModel.observeDeleting.observeForTesting {
            assertThat(viewModel.observeDeleting.value).isEqualTo(Resource.Success(true))
        }
    }

    @Test
    fun `deleteFromFavorite should delete tvShow`() = mainCoroutineRule.testDispatcher.runBlockingTest {
        //make sure TvShow is loaded
        whenever(remoteMainRepository.getDetailTvShow(dummyTvShow.id)).thenReturn(dummyTvShow)
        viewModel.submitTvShow(dummyTvShow.id)

        viewModel.observeTvShow.observeForTesting {
            assertThat(viewModel.observeTvShow.value).isEqualTo(Resource.Success(dummyTvShow))
        }

        whenever(localPersistRepository.deleteTvShowFromFavorite(dummyTvShow)).thenReturn(1)

        //call
        viewModel.deleteFromFavorite()

        verify(localPersistRepository).deleteTvShowFromFavorite(dummyTvShow)

        //assert when TvShow get deleted, observedeleting has the rowDeleted count
        viewModel.observeDeleting.observeForTesting {
            assertThat(viewModel.observeDeleting.value).isEqualTo(Resource.Success(true))
        }
    }
}