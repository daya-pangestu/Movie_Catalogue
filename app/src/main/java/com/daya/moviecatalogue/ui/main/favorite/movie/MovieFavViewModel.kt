package com.daya.moviecatalogue.ui.main.favorite.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.daya.moviecatalogue.data.main.LocalPersistRepository
import com.daya.moviecatalogue.di.coroutine.MainDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

@HiltViewModel
class MovieFavViewModel
@Inject
constructor(
    private val localPersistRepository: LocalPersistRepository,
) : ViewModel() {

    private val _favoriteMovies = localPersistRepository.getAllFavoriteMovies()

    val favoriteMovies = _favoriteMovies.cachedIn(viewModelScope)

}