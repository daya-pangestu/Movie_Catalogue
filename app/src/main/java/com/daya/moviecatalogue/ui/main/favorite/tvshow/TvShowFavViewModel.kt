package com.daya.moviecatalogue.ui.main.favorite.tvshow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.daya.moviecatalogue.data.LocalPersistRepository
import com.daya.moviecatalogue.data.main.movie.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TvShowFavViewModel
@Inject
constructor(
    private val localPersistRepository: LocalPersistRepository
) : ViewModel() {


    private val _favoriteTvShow = liveData {
        val list = localPersistRepository.getAllFavoriteTvShow().asLiveData()
        emitSource(list)
    }

    val favoriteMovies = _favoriteTvShow
}