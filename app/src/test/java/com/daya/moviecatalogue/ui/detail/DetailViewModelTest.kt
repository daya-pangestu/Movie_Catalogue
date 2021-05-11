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
    private var repository = mock<MainRepository>()

    @Before
    fun setUp() {
        viewModel = DetailViewModel(repository)
    }

    @Test
    fun `getCurrentMovieByTitle should return movie based by title`() {

        val movie = viewModel.getCurrentMovieByTitle(dummyMovie.title)

        assertThat(movie).isNotNull()
        assertThat(dummyMovie.title).isEqualTo(movie.title)
        assertThat(dummyMovie.description).isEqualTo(movie.description)
        assertThat(dummyMovie.genre).isEqualTo(movie.genre)
        assertThat(dummyMovie.image_url).isEqualTo(movie.image_url)
        assertThat(dummyMovie.rate).isEqualTo(movie.rate)
        assertThat(dummyMovie.release_date).isEqualTo(movie.release_date)
        assertThat(dummyMovie.user_score).isEqualTo(movie.user_score)
        assertThat(dummyMovie.year).isEqualTo(movie.year)
    }

    @Test
    fun `getCurrentTvShowByTitle should return tvShow based by title`() {

        val tvShow = viewModel.getCurrentTvShowByTitle(dummyTvShow.title)

        assertThat(tvShow).isNotNull()
        assertThat(dummyTvShow.title).isEqualTo(tvShow.title)
        assertThat(dummyTvShow.description).isEqualTo(tvShow.description)
        assertThat(dummyTvShow.genre).isEqualTo(tvShow.genre)
        assertThat(dummyTvShow.image_url).isEqualTo(tvShow.image_url)
        assertThat(dummyTvShow.rate).isEqualTo(tvShow.rate)
        assertThat(dummyTvShow.user_score).isEqualTo(tvShow.user_score)
        assertThat(dummyTvShow.year).isEqualTo(tvShow.year)
    }

    @Test
    fun `observeMovie should return movie based by id`() = mainCoroutineRule.testDispatcher.runBlockingTest {
        whenever(repository.getDetailMovie(dummyMovie.id)).thenReturn(dummyMovie)

        viewModel.submitMovie(dummyMovie)

        val resLoading = viewModel.observeMovie().getOrAwaitValue()
        assertThat(resLoading).isEqualTo(Resource.Loading)

        viewModel.observeMovie().observeForTesting {
            assertThat(viewModel.observeMovie().value).isEqualTo(Resource.Success(dummyMovie))
        }
    }

    @Test
    fun `observeTvShow should return tvShow based by id`() {
    }
}