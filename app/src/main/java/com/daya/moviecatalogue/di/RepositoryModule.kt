package com.daya.moviecatalogue.di

import androidx.paging.PagingSource
import com.daya.moviecatalogue.data.main.LocalMainDataSource
import com.daya.moviecatalogue.data.main.LocalPersistRepository
import com.daya.moviecatalogue.data.main.MainDataSource
import com.daya.moviecatalogue.data.main.PersistRepository
import com.daya.moviecatalogue.data.main.RemoteMainDataSource
import com.daya.moviecatalogue.data.main.movie.local.MovieEntity
import com.daya.moviecatalogue.data.main.movie.response.DetailMovieResponse
import com.daya.moviecatalogue.data.main.tvshow.local.TvShowEntity
import com.daya.moviecatalogue.data.main.tvshow.response.DetailTvShowResponse
import com.daya.moviecatalogue.data.main.tvshow.response.TvShowResponse
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
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
    abstract fun bindRemoteMainDataSource(remoteMainDataSource: RemoteMainDataSource): MainDataSource<PagingSource<Int, DetailMovieResponse>, PagingSource<Int, DetailTvShowResponse>>

    @Singleton
    @Binds
    abstract fun bindLocalMainDataSource(localMainDataSource: LocalMainDataSource): MainDataSource<PagingSource<Int, MovieEntity>, PagingSource<Int, TvShowEntity>>

}