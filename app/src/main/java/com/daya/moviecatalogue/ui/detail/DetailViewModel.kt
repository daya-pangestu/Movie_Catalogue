package com.daya.moviecatalogue.ui.detail

import androidx.lifecycle.ViewModel
import com.daya.moviecatalogue.data.DataDummy
import com.daya.moviecatalogue.data.movie.Movie
import com.daya.moviecatalogue.data.tvshow.TvShow

class DetailViewModel : ViewModel() {

    private val listMovie = DataDummy.getListMovie()
    private val listTvShow = DataDummy.getListTvShow()

    fun getCurrentMovieByTitle(title: String): Movie {
        return listMovie.first { it.title == title }
    }

    fun getCurrentTvShowByTitle(title: String): TvShow {
        return listTvShow.first { it.title == title }

    }
}