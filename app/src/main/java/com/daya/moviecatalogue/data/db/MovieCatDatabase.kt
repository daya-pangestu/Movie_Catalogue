package com.daya.moviecatalogue.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.daya.moviecatalogue.data.main.movie.local.MovieDao
import com.daya.moviecatalogue.data.main.movie.local.MovieEntity

@Database(
    entities = [MovieEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MovieCatDatabase : RoomDatabase() {
    abstract fun movieDao() :MovieDao
}