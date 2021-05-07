package com.daya.moviecatalogue.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.daya.moviecatalogue.data.DataDummy
import com.daya.moviecatalogue.data.Resource
import com.daya.moviecatalogue.data.main.MainRepository
import com.daya.moviecatalogue.di.coroutine.MainDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
@HiltViewModel
class MainViewModel
@Inject
constructor(
    private val mainRepository: MainRepository,
    @MainDispatcher coroutineDispatcher: CoroutineDispatcher
): ViewModel() {

    val getMovie = DataDummy.getListMovie()

    val getTvShow = DataDummy.getListTvShow()

    private val _discoverMovies = liveData(coroutineDispatcher) {
        emit(Resource.Loading)
        try {
            val list = mainRepository.discoverMovies()
            emit(Resource.Success(list))
        } catch (e: Exception) {
            emit(Resource.Error(e.message))
        }
    }

    val discoverMovie = _discoverMovies

    private val _discoverTvShow = liveData {
        emit(Resource.Loading)
        try {
            val list = mainRepository.discoverTvShow()
            emit(Resource.Success(list))
        } catch (e: Exception) {
            emit(Resource.Error(e.message))
        }
    }

    val discoverTvShow = _discoverTvShow

}