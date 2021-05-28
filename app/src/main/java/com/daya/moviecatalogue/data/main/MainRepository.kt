package com.daya.moviecatalogue.data.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.daya.moviecatalogue.data.main.movie.Movie
import com.daya.moviecatalogue.data.main.movie.response.DetailMovieResponse
import com.daya.moviecatalogue.data.main.tvshow.TvShow
import com.daya.moviecatalogue.mapToMovie
import com.daya.moviecatalogue.maptoTvShow
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository
@Inject
constructor(
    private val remoteDetailDataSource: RemoteDetailDataSource,
    private val remoteMainDataSource: RemoteMainDataSource,
){
    fun discoverMovies(): Flow<PagingData<Movie>> {
        return Pager(config = PagingConfig(pageSize = 20)){
            remoteMainDataSource.getListMovies()
        }.flow
            .map {
                it.map {
                    it.mapToMovie()
                }
            }
    }

    suspend fun discoverTvShow() = remoteMainDataSource.getListTvShow().results.map { it.maptoTvShow() }

    suspend fun getDetailMovie(movieId: Int): Movie = remoteDetailDataSource.getDetailMovie(movieId).mapToMovie()

    suspend fun getDetailTvShow(tvShowId: Int): TvShow = remoteDetailDataSource.getDetailTvShow(tvShowId).maptoTvShow()

}