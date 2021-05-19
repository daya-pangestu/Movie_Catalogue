package com.daya.moviecatalogue.ui.main.foryou.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
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

    private val _discoverMovies = liveData {
        emit(Resource.Loading)
        try {
            val list = mainRepository.discoverMovies()
            emit(Resource.Success(list))
        } catch (e: Exception) {
            emit(Resource.Error(e.message))
        }
    }

    val discoverMovie = _discoverMovies
}
