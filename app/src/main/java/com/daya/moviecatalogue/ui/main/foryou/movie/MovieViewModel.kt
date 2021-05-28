package com.daya.moviecatalogue.ui.main.foryou.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.daya.moviecatalogue.data.Resource
import com.daya.moviecatalogue.data.main.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieViewModel
@Inject
constructor(
    private val mainRepository: MainRepository,
): ViewModel() {

    private val _discoverMovies = mainRepository.discoverMovies().cachedIn(viewModelScope)

    val discoverMovie = _discoverMovies
}
