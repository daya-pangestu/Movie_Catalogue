package com.daya.moviecatalogue.data.main

import com.daya.moviecatalogue.data.main.movie.Movie
import com.daya.moviecatalogue.data.main.movie.response.MovieResponse
import com.daya.moviecatalogue.data.main.tvshow.TvShow
import com.daya.moviecatalogue.mapToMovie
import com.daya.moviecatalogue.maptoTvShow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository
@Inject
constructor(
        private val detailDataSource: RemoteDetailDataSource,
        private val mainDataSource: RemoteMainDataSource
){
    suspend fun discoverMovies() = mainDataSource.getListMovies().results.map { it.mapToMovie() }

    suspend fun discoverTvShow() = mainDataSource.getListTvShow().results.map { it.maptoTvShow() }

    suspend fun getDetailMovie(movieId: Int): Movie = detailDataSource.getDetailMovie(movieId).mapToMovie()

    suspend fun getDetailTvShow(tvShowId: Int): TvShow = detailDataSource.getDetailTvShow(tvShowId).maptoTvShow()

    fun isFavorite() {

    }

}