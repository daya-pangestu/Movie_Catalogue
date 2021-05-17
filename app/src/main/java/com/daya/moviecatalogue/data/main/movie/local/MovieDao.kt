package com.daya.moviecatalogue.data.main.movie.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie_entity")
    fun getMovies() : Flow<List<MovieEntity>>

    @Insert
    fun insertMovie(movie: MovieEntity)

    @Delete
    fun deleteMovie(movie: MovieEntity)

    @Query("SELECT * from movie_entity WHERE movieId = :movieId")
    fun getMovieById(movieId : Int) : Flow<MovieEntity?>
}