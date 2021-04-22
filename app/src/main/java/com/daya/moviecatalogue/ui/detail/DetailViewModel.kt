package com.daya.moviecatalogue.ui.detail

import androidx.lifecycle.ViewModel
import com.daya.moviecatalogue.data.movie.Movie
import com.daya.moviecatalogue.data.tvshow.TvShow

class DetailViewModel : ViewModel() {
    var movie: Movie? = null
    var tvShow: TvShow? = null


}