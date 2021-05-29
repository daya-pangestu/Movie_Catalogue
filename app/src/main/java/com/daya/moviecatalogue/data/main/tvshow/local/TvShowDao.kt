package com.daya.moviecatalogue.data.main.tvshow.local

import androidx.annotation.VisibleForTesting
import androidx.paging.PagingSource
import androidx.room.*
import com.daya.moviecatalogue.data.main.movie.local.MovieEntity
import com.daya.moviecatalogue.data.main.tvshow.TvShow
import kotlinx.coroutines.flow.Flow

@Dao
interface TvShowDao {

    @Query("SELECT * FROM tvShow_entity")
    fun getTvShows() : Flow<List<TvShowEntity>>

    @Query("SELECT * FROM tvShow_entity")
    fun getTvShowsPaged() : PagingSource<Int, TvShowEntity>

    @Insert
    fun insertTvShow(TvShow: TvShowEntity) : Long

    @Delete
    fun deleteTvShow(TvShow: TvShowEntity) : Int

    @Query("SELECT * from tvShow_entity WHERE tvShowId = :tvShowId")
    suspend fun getTvShowById(tvShowId: Int): TvShowEntity?

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun batchInsertTvShow(tvshow :List<TvShowEntity>) : List<Long>
}

