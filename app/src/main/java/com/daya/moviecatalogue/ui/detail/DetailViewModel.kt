package com.daya.moviecatalogue.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.daya.moviecatalogue.data.DataDummy
import com.daya.moviecatalogue.data.main.movie.Movie
import com.daya.moviecatalogue.data.main.tvshow.TvShow

class DetailViewModel : ViewModel() {

    private val listMovie = DataDummy.getListMovie()
    private val listTvShow = DataDummy.getListTvShow()

    private val movieLiveData = MutableLiveData<Movie>()

    private val tvShowLiveData = MutableLiveData<TvShow>()

    fun getCurrentMovieByTitle(title: String): Movie {
        return listMovie.first { it.title == title }
    }

    fun getCurrentTvShowByTitle(title: String): TvShow {
        return listTvShow.first { it.title == title }

    }

    fun submitTvShow(tvShow: TvShow) {
        tvShowLiveData.value = tvShow
    }


    fun submitMovie(movie: Movie) {
        movieLiveData.value = movie
    }

    fun observeMovie() = movieLiveData

    fun observeTvShow() = tvShowLiveData
}