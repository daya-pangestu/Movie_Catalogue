package com.daya.moviecatalogue.shared.data

import androidx.paging.*
import com.daya.moviecatalogue.data.main.PersistRepository
import com.daya.moviecatalogue.data.main.movie.Movie
import com.daya.moviecatalogue.data.main.movie.local.MovieEntity
import com.daya.moviecatalogue.data.main.tvshow.TvShow
import com.daya.moviecatalogue.shared.DataDummy
import com.daya.moviecatalogue.shared.mapListMovieToMovieEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FakeLocalPersistRepository @Inject constructor(): PersistRepository {
    override fun getAllFavoriteMovies(): Flow<PagingData<Movie>> {

        return Pager(
            config = PagingConfig(
                pageSize = 20,
            )
        ){
            getMoviePagingSource
        }.flow
    }

    val getMoviePagingSource =  object : PagingSource<Int, Movie>() {
        override fun getRefreshKey(state: PagingState<Int, Movie>): Int? =null
        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
            return LoadResult.Page(
                prevKey = null,
                nextKey = null,
                data = DataDummy.getListMovie()
            )
        }
    }

    override fun getAllFavoriteTvShow(): Flow<PagingData<TvShow>> {


        return Pager(
            config = PagingConfig(
                pageSize = 20,
            )
        ){
            fgetTvShowPagingSource
        }.flow    
    }

    val fgetTvShowPagingSource =  object : PagingSource<Int, TvShow>() {
        override fun getRefreshKey(state: PagingState<Int, TvShow>): Int? =null
        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TvShow> {
            return LoadResult.Page(
                prevKey = null,
                nextKey = null,
                data = DataDummy.getListTvShow()
            )
        }
    }

    override suspend fun addMovieToFavorite(movie: Movie): Long =0L
    override suspend fun addTvShowToFavorite(tvShow: TvShow): Long = 0L
    override suspend fun deleteMovieFromFavorite(movie: Movie): Int = 0
    override suspend fun deleteTvShowFromFavorite(tvShow: TvShow): Int =0
    override suspend fun isFavorite(id: Int): Boolean =false
}