package com.daya.moviecatalogue.ui.main.foryou.tvshow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
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

    private val _discoverTvShow = liveData {
        emit(Resource.Loading)
        try {
            val list = remoteMainRepository.discoverTvShow()
            emit(Resource.Success(list))
        } catch (e: Exception) {
            emit(Resource.Error(e.message))
        }
    }

    val discoverTvShow = _discoverTvShow
}