package com.daya.moviecatalogue.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.daya.moviecatalogue.data.main.movie.local.MovieDao
import com.daya.moviecatalogue.data.main.movie.local.MovieEntity
import com.daya.moviecatalogue.data.main.tvshow.local.TvShowDao
import com.daya.moviecatalogue.data.main.tvshow.local.TvShowEntity

@Database(
    entities = [
        MovieEntity::class,
        TvShowEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class MovieCatDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun TvShowDao() : TvShowDao
}