package com.daya.moviecatalogue.ui.detail

import androidx.lifecycle.*
import com.daya.moviecatalogue.data.DataDummy
import com.daya.moviecatalogue.data.Resource
import com.daya.moviecatalogue.data.main.MainRepository
import com.daya.moviecatalogue.data.main.movie.Movie
import com.daya.moviecatalogue.data.main.tvshow.TvShow
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel
@Inject
constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val listMovie = DataDummy.getListMovie()
    private val listTvShow = DataDummy.getListTvShow()

    private val movieIdLiveData = MutableLiveData<Int>()

    private val tvShowIdLiveData = MutableLiveData<Int>()

    fun submitTvShow(tvShowId: Int) {
        tvShowIdLiveData.value = tvShowId
    }

    fun submitMovie(movieId: Int) {
        movieIdLiveData.value = movieId
    }

    fun getCurrentMovieById(title: String): Movie {
        return listMovie.first { it.title == title }
    }

    fun getCurrentTvShowById(title: String): TvShow {
        return listTvShow.first { it.title == title }
    }

    fun observeMovie() = movieIdLiveData.switchMap {
        liveData {
            emit(Resource.Loading)
            try {
                val data = repository.getDetailMovie(it)
                emit(Resource.Success(data))
            } catch (e: Exception) {
                emit(Resource.Error(e.message))
            }
        }
    }

    fun observeTvShow() = tvShowIdLiveData.switchMap {
        liveData {
            emit(Resource.Loading)
            try {
                val data = repository.getDetailTvShow(it)
                emit(Resource.Success(data))
            } catch (e: Exception) {
                emit(Resource.Error(e.message))
            }
        }
    }
}