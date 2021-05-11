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

    private val movieLiveData = MutableLiveData<Movie>()

    private val tvShowLiveData = MutableLiveData<TvShow>()

    fun submitTvShow(tvShow: TvShow) {
        tvShowLiveData.value = tvShow
    }

    fun submitMovie(movie: Movie) {
        movieLiveData.value = movie
    }

    fun getCurrentMovieByTitle(title: String): Movie {
        return listMovie.first { it.title == title }
    }

    fun getCurrentTvShowByTitle(title: String): TvShow {
        return listTvShow.first { it.title == title }
    }

    fun observeMovie() = movieLiveData.switchMap {
        liveData {
            try {
                emit(Resource.Loading)
                val data = repository.getDetailMovie(it.id)
                emit(Resource.Success(data))
            } catch (e: Exception) {
                emit(Resource.Error(e.message))
            }
        }
    }

    fun observeTvShow() = tvShowLiveData.switchMap {
        liveData {
            try {
                emit(Resource.Loading)
                val data = repository.getDetailTvShow(it.id)
                emit(Resource.Success(data))
            } catch (e: Exception) {
                emit(Resource.Error(e.message))
            }
        }
    }
}