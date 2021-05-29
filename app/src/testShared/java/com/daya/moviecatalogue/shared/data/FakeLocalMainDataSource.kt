package com.daya.moviecatalogue.shared.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.daya.moviecatalogue.data.main.MainDataSource
import com.daya.moviecatalogue.data.main.movie.Movie
import com.daya.moviecatalogue.data.main.movie.local.MovieEntity
import com.daya.moviecatalogue.data.main.tvshow.TvShow
import com.daya.moviecatalogue.data.main.tvshow.local.TvShowEntity
import com.daya.moviecatalogue.mapListMovieToMovieEntity
import com.daya.moviecatalogue.shared.DataDummy
import com.daya.moviecatalogue.shared.mapListTvShowToTvShowEntity
import javax.inject.Inject

class FakeLocalMainDataSource
@Inject
constructor() :  MainDataSource<PagingSource<Int, MovieEntity>, PagingSource<Int, TvShowEntity>>  {
    override fun getListMovies(): PagingSource<Int, MovieEntity> {
        return object : PagingSource<Int, MovieEntity>() {
            override fun getRefreshKey(state: PagingState<Int, MovieEntity>): Int? =null
            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieEntity> {
                return LoadResult.Page(
                    prevKey = null,
                    nextKey = null,
                    data = DataDummy.getListMovie().mapListMovieToMovieEntity()
                )
            }
        }
    }

    override fun getListTvShow(): PagingSource<Int, TvShowEntity> {
        return object : PagingSource<Int, TvShowEntity>() {
            override fun getRefreshKey(state: PagingState<Int, TvShowEntity>): Int? =null
            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TvShowEntity> {
                return LoadResult.Page(
                    prevKey = null,
                    nextKey = null,
                    data = DataDummy.getListTvShow().mapListTvShowToTvShowEntity()
                )
            }
        }
    }
}