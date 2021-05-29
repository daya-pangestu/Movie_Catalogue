package com.daya.moviecatalogue.shared.data

import androidx.paging.*
import com.daya.moviecatalogue.data.main.MainDataSource
import com.daya.moviecatalogue.data.main.movie.response.DetailMovieResponse
import com.daya.moviecatalogue.data.main.tvshow.response.DetailTvShowResponse
import com.daya.moviecatalogue.data.main.tvshow.response.TvShowResponse
import com.daya.moviecatalogue.mapToMovie
import com.daya.moviecatalogue.shared.DataDummy
import com.daya.moviecatalogue.shared.mapListMovieToDetailMovieResponse
import com.daya.moviecatalogue.shared.mapListTvShowToDetailTvShowResponse
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FakeRemoteMainDataSource
@Inject
constructor() :MainDataSource<PagingSource<Int, DetailMovieResponse>, PagingSource<Int, DetailTvShowResponse>> {
    //TODO replace tvshow with paging3 implementation
    override fun getListMovies(): PagingSource<Int, DetailMovieResponse> {
        return object : PagingSource<Int, DetailMovieResponse>() {
            //not needed in testing
            override fun getRefreshKey(state: PagingState<Int, DetailMovieResponse>): Int? = null

            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DetailMovieResponse> {
                return LoadResult.Page(
                    data = DataDummy.getListMovie().mapListMovieToDetailMovieResponse(),
                    nextKey = null,
                    prevKey = null //TODO paging to previous page
                )
            }
        }
    }

    override fun getListTvShow(): PagingSource<Int, DetailTvShowResponse> {
        return object : PagingSource<Int, DetailTvShowResponse>() {
            //not needed in testing
            override fun getRefreshKey(state: PagingState<Int, DetailTvShowResponse>): Int? = null

            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DetailTvShowResponse> {
                return LoadResult.Page(
                    data = DataDummy.getListTvShow().mapListTvShowToDetailTvShowResponse(),
                    nextKey = null,
                    prevKey = null //TODO paging to previous page
                )
            }
        }
    }
}

