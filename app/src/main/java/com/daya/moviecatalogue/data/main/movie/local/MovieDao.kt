package com.daya.moviecatalogue.data.main.movie.local

import androidx.annotation.VisibleForTesting
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie_entity")
    fun getMovies(): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieEntity) : Long

    @Delete
    suspend fun deleteMovie(movie: MovieEntity) : Int

    @Query("SELECT * from movie_entity WHERE movieId = :movieId")
    suspend fun getMovieById(movieId: Int): MovieEntity?

    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun batchInsertMovie(movies: List<MovieEntity>)

}
