package com.daya.moviecatalogue.data.main

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import com.daya.moviecatalogue.data.main.movie.Movie
import com.daya.moviecatalogue.data.main.tvshow.TvShow
import com.daya.moviecatalogue.di.coroutine.IoDispatcher
import com.daya.moviecatalogue.mapToMovieEntity
import com.daya.moviecatalogue.mapToMovie
import com.daya.moviecatalogue.mapToTvShow
import com.daya.moviecatalogue.mapToTvShowEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

class LocalPersistRepository
@Inject
constructor(
    private val localPersistDataSource: LocalPersistDataSource,
    private val localMainDataSource: LocalMainDataSource,
    private val localDetailDataSource: LocalDetailDataSource,
    private val externalScope : CoroutineScope,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) :PersistRepository {
   override fun getAllFavoriteMovies() =
       Pager(
           config = PagingConfig(
               pageSize = 20,
           )
       ) {
           localMainDataSource.getListMovies()
       }.flow
           .map { pagingData ->
               pagingData
                   .map {
                       it.mapToMovie()
                   }
           }

    override fun getAllFavoriteTvShow() = localMainDataSource.getListTvShow().map {
        it.map { it.mapToTvShow()}
    }

    override suspend fun addMovieToFavorite(movie: Movie) = externalScope.async(coroutineDispatcher) {
        val entity = movie.mapToMovieEntity()
       localPersistDataSource.addMovieToFavorite(entity)
    }.await()

    override suspend fun addTvShowToFavorite(tvShow: TvShow) = externalScope.async(coroutineDispatcher) {
        val entity = tvShow.mapToTvShowEntity()
        localPersistDataSource.addTvShowToFavorte(entity)
    }.await()

    override suspend fun deleteMovieFromFavorite(movie: Movie)= externalScope.async(coroutineDispatcher) {
        val entity = movie.mapToMovieEntity()
        localPersistDataSource.deleteMovieFromFavorite(entity)
    }.await()

    override suspend fun deleteTvShowFromFavorite(tvShow: TvShow) = externalScope.async(coroutineDispatcher){
        val entity = tvShow.mapToTvShowEntity()
        localPersistDataSource.deleteTvShowFromFavorite(entity)
    }.await()

    override suspend fun isFavorite(id : Int): Boolean {
        val movie = localDetailDataSource.getDetailMovie(id)
        val tvShow = localDetailDataSource.getDetailTvShow(id)
        return movie != null || tvShow!= null
    }
}