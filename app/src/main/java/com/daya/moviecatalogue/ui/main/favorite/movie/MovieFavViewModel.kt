package com.daya.moviecatalogue.ui.main.favorite.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.daya.moviecatalogue.data.LocalPersistRepository
import com.daya.moviecatalogue.data.Resource
import com.daya.moviecatalogue.data.main.MainRepository
import com.daya.moviecatalogue.data.main.movie.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class MovieFavViewModel
@Inject
constructor(
    private val localPersistRepository: LocalPersistRepository
) : ViewModel() {
    private val _favoriteMovies = liveData {
        val list = localPersistRepository.getAllFavoriteMovies().asLiveData()
        emitSource(list)
    }

    val favoriteMovies = _favoriteMovies
}