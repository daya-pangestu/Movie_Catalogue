package com.daya.moviecatalogue.data.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.daya.moviecatalogue.data.main.movie.Movie
import com.daya.moviecatalogue.data.main.tvshow.TvShow
import com.daya.moviecatalogue.mapToMovie
import com.daya.moviecatalogue.maptoTvShow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.zip
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository
@Inject
constructor(
    private val remoteDetailDataSource: RemoteDetailDataSource,
    private val localDetailDataSource: LocalDetailDataSource,
    private val remoteMainDataSource: RemoteMainDataSource,
    private val localMainDataSource: LocalMainDataSource
){
    suspend fun discoverMovies() = remoteMainDataSource.getListMovies().results.map { it.mapToMovie() }

    suspend fun discoverTvShow() = remoteMainDataSource.getListTvShow().results.map { it.maptoTvShow() }

    suspend fun getDetailMovie(movieId: Int): Movie = remoteDetailDataSource.getDetailMovie(movieId).mapToMovie()

    suspend fun getDetailTvShow(tvShowId: Int): TvShow = remoteDetailDataSource.getDetailTvShow(tvShowId).maptoTvShow()


    suspend fun isFavorite(id : Int): Flow<Boolean> {
        return localDetailDataSource.getDetailMovie(id)
            .zip(localDetailDataSource.getDetailTvShow(id)){ movieEntity, tvShowEntity ->
                movieEntity != null || tvShowEntity != null
            }

    }
}