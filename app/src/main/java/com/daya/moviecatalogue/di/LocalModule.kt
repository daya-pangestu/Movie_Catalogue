package com.daya.moviecatalogue.di

import android.content.Context
import androidx.room.Room
import com.daya.moviecatalogue.data.db.MovieCatDatabase
import com.daya.moviecatalogue.data.main.movie.local.MovieDao
import com.daya.moviecatalogue.data.main.tvshow.local.TvShowDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object LocalModule {

    @Singleton
    @Provides
    fun providesDatabase(@ApplicationContext context : Context) : MovieCatDatabase = Room
        .databaseBuilder(
            context,
            MovieCatDatabase::class.java,
            "MovieCat.db"
        )
        .fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun providesMovieDao(db : MovieCatDatabase) : MovieDao = db.movieDao()

    @Singleton
    @Provides
    fun provideTvShowDao(db: MovieCatDatabase) : TvShowDao = db.TvShowDao()
}