package com.daya.moviecatalogue.ui.main.foryou.tvshow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.daya.moviecatalogue.data.Resource
import com.daya.moviecatalogue.data.main.RemoteMainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TvShowViewModel
@Inject
constructor(
    private val remoteMainRepository: RemoteMainRepository
) : ViewModel() {

    private val _discoverTvShow = remoteMainRepository.discoverTvShow().cachedIn(viewModelScope)

    val discoverTvShow = _discoverTvShow
}