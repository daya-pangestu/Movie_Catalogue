package com.daya.moviecatalogue.shared

import androidx.paging.*
import com.daya.moviecatalogue.data.main.PersistRepository
import com.daya.moviecatalogue.data.main.movie.Movie
import com.daya.moviecatalogue.data.main.movie.local.MovieEntity
import com.daya.moviecatalogue.data.main.tvshow.TvShow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FakeLocalPersistRepository @Inject constructor(): PersistRepository {
    override fun getAllFavoriteMovies(): Flow<PagingData<Movie>> {
       val fakeGetMoviePagingSource =  object : PagingSource<Int, Movie>() {
            override fun getRefreshKey(state: PagingState<Int, Movie>): Int? =null
            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
                return LoadResult.Page(
                    prevKey = null,
                    nextKey = null,
                    data = DataDummy.getListMovie()
                )
            }
        }

        return Pager(
            config = PagingConfig(
                pageSize = 20,
            )
        ){
            fakeGetMoviePagingSource
        }.flow
    }

    override fun getAllFavoriteTvShow(): Flow<List<TvShow>> {
        TODO("Not yet implemented")
    }

    override suspend fun addMovieToFavorite(movie: Movie): Long {
        TODO("Not yet implemented")
    }

    override suspend fun addTvShowToFavorite(tvShow: TvShow): Long {
        TODO("Not yet implemented")
    }

    override suspend fun deleteMovieFromFavorite(movie: Movie): Int {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTvShowFromFavorite(tvShow: TvShow): Int {
        TODO("Not yet implemented")
    }

    override suspend fun isFavorite(id: Int): Boolean {
        TODO("Not yet implemented")
    }
}