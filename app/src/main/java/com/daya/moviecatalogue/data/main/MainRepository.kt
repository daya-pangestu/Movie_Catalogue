package com.daya.moviecatalogue.data.main

import androidx.paging.PagingData
import com.daya.moviecatalogue.data.main.movie.Movie
import com.daya.moviecatalogue.data.main.tvshow.TvShow
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    fun discoverMovies(): Flow<PagingData<Movie>>
    suspend fun discoverTvShow() : List<TvShow>
    suspend fun getDetailMovie(movieId: Int): Movie
    suspend fun getDetailTvShow(tvShowId: Int): TvShow
}