package com.daya.moviecatalogue.data.main.tvshow.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TvShowDao {

    @Query("SELECT * FROM tvShow_entity")
    fun getTvShows() : Flow<List<TvShowEntity>>

    @Insert
    fun insertTvShow(TvShow: TvShowEntity)

    @Delete
    fun deleteTvShow(TvShow: TvShowEntity)

    @Query("SELECT * from tvShow_entity WHERE tvShowId = :tvShowId")
    fun getTvShowById(tvShowId: Int): Flow<TvShowEntity?>
}