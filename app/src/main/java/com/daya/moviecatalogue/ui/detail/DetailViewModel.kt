package com.daya.moviecatalogue.ui.detail

import androidx.lifecycle.*
import com.daya.moviecatalogue.data.main.LocalPersistRepository
import com.daya.moviecatalogue.data.Resource
import com.daya.moviecatalogue.data.main.RemoteMainRepository
import com.daya.moviecatalogue.data.main.movie.Movie
import com.daya.moviecatalogue.data.main.tvshow.TvShow
import com.daya.moviecatalogue.di.idlingresource.TestIdlingResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel
@Inject
constructor(
    private val remoteMainRepository: RemoteMainRepository,
    private val localPersistRepository: LocalPersistRepository
) : ViewModel() {
    private val movieIdLiveData = MutableLiveData<Int>()
    private val tvShowIdLiveData = MutableLiveData<Int>()

    private val idIsFavorite = MutableLiveData<Int>()

    private val _savingProgress = MutableLiveData<Resource<Boolean>>()
    private val _deletingProgress = MutableLiveData<Resource<Boolean>>()

    var isFavorite = false

    fun submitTvShow(tvShowId: Int) {
        tvShowIdLiveData.value = tvShowId
    }

    fun submitMovie(movieId: Int) {
        movieIdLiveData.value = movieId
    }

    fun startObserveIsFavorite(id: Int) {
        idIsFavorite.value = id
    }

    val _observeMovie = movieIdLiveData.switchMap {
        liveData {
            emit(Resource.Loading)
            try {
                TestIdlingResource.increment()
                val data = remoteMainRepository.getDetailMovie(it)
                emit(Resource.Success(data))
            } catch (e: Exception) {
                emit(Resource.Error(e.message))
            }finally {
                TestIdlingResource.decrement()
            }
        }
    }

    val observeMovie = _observeMovie

    val _observeTvShow = tvShowIdLiveData.switchMap {
        liveData {
            emit(Resource.Loading)
            try {
                TestIdlingResource.increment()
                val data = remoteMainRepository.getDetailTvShow(it)
                emit(Resource.Success(data))

            } catch (e: Exception) {
                emit(Resource.Error(e.message))
            }finally {
                TestIdlingResource.decrement()
            }
        }
    }

    val observeTvShow = _observeTvShow

    //one-shot operation
    val checkIsFavorite = idIsFavorite.switchMap {
       liveData {
           emit(Resource.Loading)
           try {
               val data = localPersistRepository.isFavorite(it)
               isFavorite = data
               emit(Resource.Success(data))
           } catch (e: Exception) {
               emit(Resource.Error(e.localizedMessage))
           }
       }
    }

    fun addToFavorite() = viewModelScope.launch {
        sanitizeMovie(observeMovie.value){
            saveMovie(it)
        }?:sanitaizeTvShow(observeTvShow.value){
            saveTvShow(it)
        }
    }

    private fun saveMovie(movie: Movie) = viewModelScope.launch {
        _savingProgress.value = Resource.Loading
        try {
            val rowId = async {
                localPersistRepository.addMovieToFavorite(movie)
            }.await()
            _savingProgress.value = Resource.Success(rowId > 0)
        } catch (e: Exception) {
            _savingProgress.value = Resource.Error(e.message)
        }
    }

    private fun saveTvShow(tvShow: TvShow) = viewModelScope.launch {
        _savingProgress.value = Resource.Loading
        try {
            val rowId = async {
                localPersistRepository.addTvShowToFavorite(tvShow)
            }.await()
            _savingProgress.value = Resource.Success(rowId>0)
        } catch (e: Exception) {
            _savingProgress.value = Resource.Error(e.message)
        }
    }

    val observeSaving = _savingProgress

    fun deleteFromFavorite(){
        sanitizeMovie(observeMovie.value){
            deleteMovie(it)
        }?: sanitaizeTvShow(observeTvShow.value){
            deleteTvShow(it)
        }
    }

    private fun deleteMovie(movie: Movie) = viewModelScope.launch {
        _deletingProgress.value = Resource.Loading
        try {
            val rowDeleted = async {
                localPersistRepository.deleteMovieFromFavorite(movie)
            }.await()
            _deletingProgress.value = Resource.Success(rowDeleted > 0)
        } catch (e: Exception) {
            _deletingProgress.value = Resource.Error(e.message)
        }
    }

    private fun deleteTvShow(tvShow: TvShow) = viewModelScope.launch {
        _deletingProgress.value = Resource.Loading
        try {
            val rowDeleted = async {
                localPersistRepository.deleteTvShowFromFavorite(tvShow)
            }.await()
            _deletingProgress.value = Resource.Success(rowDeleted > 0)
        } catch (e: Exception) {
            _deletingProgress.value = Resource.Error(e.message)
        }
    }

    val observeDeleting = _deletingProgress

    private fun sanitizeMovie(resource: Resource<Movie>?, actionNonNull: (Movie) -> Unit) =
        when (resource) {
            is Resource.Success -> {
                actionNonNull(resource.data)
                resource.data
            }
            else -> null
        }

    private fun sanitaizeTvShow(resource: Resource<TvShow>?, actionNonNull: (TvShow) -> Unit) = when (resource) {
        is Resource.Success -> {
            actionNonNull(resource.data)
            resource.data
        }
        else -> null
    }
}