package com.daya.moviecatalogue.ui.detail

import androidx.lifecycle.*
import com.daya.moviecatalogue.data.LocalPersistRepository
import com.daya.moviecatalogue.data.Resource
import com.daya.moviecatalogue.data.main.MainRepository
import com.daya.moviecatalogue.data.main.movie.Movie
import com.daya.moviecatalogue.data.main.tvshow.TvShow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailViewModel
@Inject
constructor(
    private val mainRepository: MainRepository,
    private val localPersistRepository: LocalPersistRepository
) : ViewModel() {
    private val movieIdLiveData = MutableLiveData<Int>()
    private val tvShowIdLiveData = MutableLiveData<Int>()

    private val idIsFavorite = MutableLiveData<Int>()

    private val _savingProgress = MutableLiveData<Resource<Long>>()
    private val _deletingProgress = MutableLiveData<Resource<Int>>()

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
                val data = mainRepository.getDetailMovie(it)
                emit(Resource.Success(data))
            } catch (e: Exception) {
                emit(Resource.Error(e.message))
            }
        }
    }

    val observeMovie = _observeMovie


    val _observeTvShow = tvShowIdLiveData.switchMap {
        liveData {
            emit(Resource.Loading)
            try {
                val data = mainRepository.getDetailTvShow(it)
                emit(Resource.Success(data))

            } catch (e: Exception) {
                emit(Resource.Error(e.message))
            }
        }
    }

    val observeTvShow = _observeTvShow

    val observeIsFavorite = idIsFavorite.switchMap {
       liveData {
           emit(Resource.Loading)
           try {
               val data = localPersistRepository.isFavorite(it)
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
        } ?: run {
            Timber.d("both movie and tvshow are null ")
        }
    }

    private fun saveMovie(movie: Movie) = viewModelScope.launch {
        _savingProgress.value = Resource.Loading
        try {
            val rowId = async {
                localPersistRepository.addMovieToFavorite(movie)
            }.await()
            _savingProgress.value = Resource.Success(rowId)
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
            _savingProgress.value = Resource.Success(rowId)
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
        } ?: run {
            Timber.d("both movie and tvshow are null")
        }
    }

    private fun deleteMovie(movie: Movie) = viewModelScope.launch {
        _deletingProgress.value = Resource.Loading
        try {
            val rowDeleted = async {
                localPersistRepository.deleteMovieFromFavorite(movie)
            }.await()

            _deletingProgress.value = Resource.Success(rowDeleted)
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
            _deletingProgress.value = Resource.Success(rowDeleted)
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