package com.daya.moviecatalogue.data.main

import com.daya.moviecatalogue.data.main.movie.response.DetailMovie
import com.daya.moviecatalogue.data.main.tvshow.response.DetailTvShow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class MainRepository
@Inject
constructor(
        private val detailDataSource: RemoteDetailDataSource,
        private val mainDataSource: RemoteMainDataSource
){
    suspend fun discoverMovies() = mainDataSource.getListMovies()

    suspend fun discoverTvShow() = mainDataSource.getListTvShow()

    suspend fun getDetailMovie(movieId: Int): DetailMovie = detailDataSource.getDetailMovie(movieId)

    suspend fun getDetailTvShow(tvShowId: Int): DetailTvShow = detailDataSource.getDetailTvShow(tvShowId)

}