package com.daya.moviecatalogue.ui.main.favorite.tvshow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.daya.moviecatalogue.data.main.LocalPersistRepository
import com.daya.moviecatalogue.data.main.PersistRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TvShowFavViewModel
@Inject
constructor(
    private val localPersistRepository: PersistRepository
) : ViewModel() {

    private val _favoriteTvShows = localPersistRepository.getAllFavoriteTvShow().cachedIn(viewModelScope)

    val favoriteTvShows = _favoriteTvShows
}