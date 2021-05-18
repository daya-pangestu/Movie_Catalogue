package com.daya.moviecatalogue.data.main

import com.daya.moviecatalogue.data.main.movie.Movie
import com.daya.moviecatalogue.data.main.movie.local.MovieDao
import com.daya.moviecatalogue.data.main.movie.local.MovieEntity
import com.daya.moviecatalogue.data.main.tvshow.TvShow
import com.daya.moviecatalogue.data.main.tvshow.local.TvShowDao
import com.daya.moviecatalogue.data.main.tvshow.local.TvShowEntity
import javax.inject.Inject
import javax.inject.Singleton

interface PersistDataSource {
    fun addMovieToFavorite(movie: MovieEntity) : Long
    suspend fun addTvShowToFavorte(tvShow: TvShowEntity): Long

    suspend fun deleteMovieFromFavorite(movie: MovieEntity): Int
    suspend fun deleteTvShowFromFavorite(tvshow : TvShowEntity): Int
}

@Singleton
class LocalPersistDataSource
@Inject
constructor(
    private val movieDao: MovieDao,
    private val tvShowDao: TvShowDao,
) : PersistDataSource {
    override fun addMovieToFavorite(movie: MovieEntity): Long {
        return movieDao.insertMovie(movie)
    }

    override suspend fun addTvShowToFavorte(tvShow: TvShowEntity): Long {
       return tvShowDao.insertTvShow(tvShow)
    }

    override suspend fun deleteMovieFromFavorite(movie: MovieEntity): Int {
        return movieDao.deleteMovie(movie)
    }

    override suspend fun deleteTvShowFromFavorite(tvshow: TvShowEntity): Int {
        return tvShowDao.deleteTvShow(tvshow)
    }
}