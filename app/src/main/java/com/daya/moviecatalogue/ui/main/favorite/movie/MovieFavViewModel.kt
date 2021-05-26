package com.daya.moviecatalogue.ui.main.favorite.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.daya.moviecatalogue.data.main.LocalPersistRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieFavViewModel
@Inject
constructor(
    private val localPersistRepository: LocalPersistRepository
) : ViewModel() {

    val favoriteMovies = localPersistRepository.getAllFavoriteMovies().cachedIn(viewModelScope)
}