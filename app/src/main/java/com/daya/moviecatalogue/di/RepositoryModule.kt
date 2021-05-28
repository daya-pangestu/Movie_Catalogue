package com.daya.moviecatalogue.di

import androidx.paging.PagingSource
import com.daya.moviecatalogue.data.main.LocalPersistRepository
import com.daya.moviecatalogue.data.main.MainDataSource
import com.daya.moviecatalogue.data.main.PersistRepository
import com.daya.moviecatalogue.data.main.RemoteMainDataSource
import com.daya.moviecatalogue.data.main.movie.response.DetailMovieResponse
import com.daya.moviecatalogue.data.main.tvshow.response.TvShowResponse
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
//easier for testing
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindLocalPersistRepository(localPersistRepository: LocalPersistRepository): PersistRepository

    @Singleton
    @Binds
    abstract fun bindRemoteMainDataSource(remoteMainDataSource: RemoteMainDataSource) : MainDataSource<PagingSource<Int, DetailMovieResponse>, TvShowResponse>

}