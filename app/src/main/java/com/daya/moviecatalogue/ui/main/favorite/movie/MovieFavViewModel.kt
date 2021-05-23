package com.daya.moviecatalogue.ui.main.favorite.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.daya.moviecatalogue.data.main.LocalPersistRepository
import dagger.hilt.android.lifecycle.HiltViewModel
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