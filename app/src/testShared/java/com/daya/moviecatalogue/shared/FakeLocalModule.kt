package com.daya.moviecatalogue.shared

import android.content.Context
import androidx.room.Room
import com.daya.moviecatalogue.data.db.MovieCatDatabase
import com.daya.moviecatalogue.data.main.movie.local.MovieDao
import com.daya.moviecatalogue.data.main.tvshow.local.TvShowDao
import com.daya.moviecatalogue.di.LocalModule
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [LocalModule::class]
)
@Module
class FakeLocalModule {
        @Singleton
        @Provides
        fun providesDatabase(@ApplicationContext context : Context) : MovieCatDatabase = Room
            .inMemoryDatabaseBuilder(
                context,
                MovieCatDatabase::class.java
            )
            .build()

        @Singleton
        @Provides
        fun providesMovieDao(db : MovieCatDatabase) : MovieDao = db.movieDao()

        @Singleton
        @Provides
        fun provideTvShowDao(db: MovieCatDatabase) : TvShowDao = db.TvShowDao()
}