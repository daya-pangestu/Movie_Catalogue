package com.daya.moviecatalogue.data.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.paging.*
import com.daya.moviecatalogue.data.main.movie.Movie
import com.daya.moviecatalogue.data.main.movie.response.DetailMovieResponse
import com.daya.moviecatalogue.data.main.tvshow.TvShow
import com.daya.moviecatalogue.data.main.tvshow.response.TvShowResponse
import com.daya.moviecatalogue.mapToMovie
import com.daya.moviecatalogue.maptoTvShow
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteMainRepository
@Inject
constructor(
    private val remoteDetailDataSource: RemoteDetailDataSource,
    private val remoteMainDataSource: MainDataSource<PagingSource<Int, DetailMovieResponse>, TvShowResponse>,
) : MainRepository{
    override fun discoverMovies(): Flow<PagingData<Movie>> {
        return Pager(config = PagingConfig(pageSize = 20)){
            remoteMainDataSource.getListMovies()
        }.flow
            .map {
                it.map {
                    it.mapToMovie()
                }
            }
    }

    override suspend fun discoverTvShow() : List<TvShow> = remoteMainDataSource.getListTvShow().results.map { it.maptoTvShow() }

    override suspend fun getDetailMovie(movieId: Int): Movie = remoteDetailDataSource.getDetailMovie(movieId).mapToMovie()

    override suspend fun getDetailTvShow(tvShowId: Int): TvShow = remoteDetailDataSource.getDetailTvShow(tvShowId).maptoTvShow()

}