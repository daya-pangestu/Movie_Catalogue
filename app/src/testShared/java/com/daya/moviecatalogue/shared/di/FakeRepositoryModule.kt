package com.daya.moviecatalogue.shared.di

import androidx.paging.PagingSource
import com.daya.moviecatalogue.data.main.MainDataSource
import com.daya.moviecatalogue.data.main.PersistRepository
import com.daya.moviecatalogue.data.main.RemoteMainDataSource
import com.daya.moviecatalogue.data.main.movie.response.DetailMovieResponse
import com.daya.moviecatalogue.data.main.tvshow.response.TvShowResponse
import com.daya.moviecatalogue.di.RepositoryModule
import com.daya.moviecatalogue.shared.data.FakeLocalPersistRepository
import com.daya.moviecatalogue.shared.data.FakeRemoteMainDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RepositoryModule::class]
)
abstract class FakeRepositoryModule {

    @Singleton
    @Binds
    abstract fun bindFakeLocalPersistRepository(fakeLocalPersistRepository: FakeLocalPersistRepository): PersistRepository

    @Singleton
    @Binds
    abstract fun bindFakeRemoteMainDataSource(fakeRemoteMainDataSource: FakeRemoteMainDataSource) : MainDataSource<PagingSource<Int, DetailMovieResponse>, TvShowResponse>

}