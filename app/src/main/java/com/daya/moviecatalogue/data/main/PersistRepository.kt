package com.daya.moviecatalogue.data.main

import androidx.paging.PagingData
import com.daya.moviecatalogue.data.main.movie.Movie
import kotlinx.coroutines.flow.Flow

interface PersistRepository {
    fun getAllFavoriteMovies(): Flow<PagingData<Movie>>

}