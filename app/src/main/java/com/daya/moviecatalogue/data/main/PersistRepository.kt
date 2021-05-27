package com.daya.moviecatalogue.data.main

import androidx.paging.PagingData
import com.daya.moviecatalogue.data.main.movie.Movie
import com.daya.moviecatalogue.data.main.tvshow.TvShow
import kotlinx.coroutines.flow.Flow

interface PersistRepository {
    fun getAllFavoriteMovies(): Flow<PagingData<Movie>>
    fun getAllFavoriteTvShow(): Flow<List<TvShow>>
    suspend fun addMovieToFavorite(movie: Movie): Long
    suspend fun addTvShowToFavorite(tvShow: TvShow): Long
    suspend fun deleteMovieFromFavorite(movie: Movie): Int
    suspend fun deleteTvShowFromFavorite(tvShow: TvShow): Int
    suspend fun isFavorite(id : Int): Boolean
}