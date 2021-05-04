package com.daya.moviecatalogue.ui.detail

import com.daya.moviecatalogue.data.DataDummy
import com.daya.moviecatalogue.data.main.movie.Movie
import com.daya.moviecatalogue.data.main.tvshow.TvShow
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class DetailViewModelTest {

    private lateinit var viewModel: DetailViewModel
    private lateinit var dummyMovie : Movie
    private lateinit var dummyTvShow : TvShow

    @Before
    fun setUp() {
        viewModel = DetailViewModel()
        dummyMovie = DataDummy.getListMovie()[1]
        dummyTvShow = DataDummy.getListTvShow()[7]
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
}