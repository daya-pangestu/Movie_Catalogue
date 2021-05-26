package com.daya.moviecatalogue.data.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import com.daya.moviecatalogue.data.main.movie.Movie
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
    private val remoteDiscoverMoviesPagingSource: RemoteDiscoverMoviesPagingSource
){
    fun discoverMovies() = Pager(
        config = PagingConfig(pageSize = 20 ),
    ){
        remoteDiscoverMoviesPagingSource
    }.flow
        .map { pagingData ->
            pagingData.map {
                it.mapToMovie()
            }
        }

    suspend fun discoverTvShow() = remoteMainDataSource.getListTvShow().results.map { it.maptoTvShow() }

    suspend fun getDetailMovie(movieId: Int): Movie = remoteDetailDataSource.getDetailMovie(movieId).mapToMovie()

    suspend fun getDetailTvShow(tvShowId: Int): TvShow = remoteDetailDataSource.getDetailTvShow(tvShowId).maptoTvShow()

}