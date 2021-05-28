package com.daya.moviecatalogue.ui.main.foryou.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.daya.moviecatalogue.data.main.RemoteMainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieViewModel
@Inject
constructor(
    private val remoteMainRepository: RemoteMainRepository,
): ViewModel() {

    private val _discoverMovies = remoteMainRepository.discoverMovies().cachedIn(viewModelScope)

    val discoverMovie = _discoverMovies
}
