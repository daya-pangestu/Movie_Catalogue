package com.daya.moviecatalogue.data

import com.daya.moviecatalogue.data.main.LocalDetailDataSource
import com.daya.moviecatalogue.data.main.LocalMainDataSource
import com.daya.moviecatalogue.data.main.LocalPersistDataSource
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

@Singleton
class LocalPersistRepository
@Inject
constructor(
    private val localPersistRepository: LocalPersistDataSource,
    private val localMainDataSource: LocalMainDataSource,
    private val localDetailDataSource: LocalDetailDataSource,
    private val externalScope : CoroutineScope,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) {

    suspend fun getAllFavoriteMovies() = localMainDataSource.getListMovies().map {
        it.map { it.mapToMovie() }
    }

    suspend fun getAllFavoriteTvShow() = localMainDataSource.getListTvShow().map {
        it.map { it.mapToTvShow()}

    }

     suspend fun addMovieToFavorite(movie: Movie) = externalScope.async(coroutineDispatcher) {
        val entity = movie.mapToMovieEntity()
       return@async localPersistRepository.addMovieToFavorite(entity)
    }.await()

    suspend fun addTvShowToFavorite(tvShow: TvShow) = externalScope.async(coroutineDispatcher) {
        val entity = tvShow.mapToTvShowEntity()
        localPersistRepository.addTvShowToFavorte(entity)
    }.await()

    suspend fun deleteMovieFromFavorite(movie: Movie)= externalScope.async(coroutineDispatcher) {
        val entity = movie.mapToMovieEntity()
        localPersistRepository.deleteMovieFromFavorite(entity)
    }.await()

    suspend fun deleteTvShowFromFavorite(tvShow: TvShow) = externalScope.async(coroutineDispatcher){
        val entity = tvShow.mapToTvShowEntity()
        localPersistRepository.deleteTvShowFromFavorite(entity)

    }.await()

    suspend fun isFavorite(id : Int): Boolean {
        val movie = localDetailDataSource.getDetailMovie(id)
        val tvShow = localDetailDataSource.getDetailTvShow(id)
        return movie != null || tvShow!= null
    }
}