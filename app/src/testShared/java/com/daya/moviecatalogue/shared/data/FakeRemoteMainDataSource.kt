package com.daya.moviecatalogue.shared.data

import androidx.paging.*
import com.daya.moviecatalogue.data.main.MainDataSource
import com.daya.moviecatalogue.data.main.movie.response.DetailMovieResponse
import com.daya.moviecatalogue.data.main.tvshow.response.TvShowResponse
import com.daya.moviecatalogue.mapToMovie
import com.daya.moviecatalogue.shared.DataDummy
import com.daya.moviecatalogue.shared.mapListMovieToDetailMovieResponse
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FakeRemoteMainDataSource @Inject constructor(

):MainDataSource<PagingSource<Int, DetailMovieResponse>, TvShowResponse> {
    //TODO replace tvshow with paging3 implementation
    override fun getListMovies(): PagingSource<Int, DetailMovieResponse> {
        return object : PagingSource<Int, DetailMovieResponse>() {
            //not needed in testing
            override fun getRefreshKey(state: PagingState<Int, DetailMovieResponse>): Int? = null

            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DetailMovieResponse> {
                return LoadResult.Page(
                    data = DataDummy.getListMovie().mapListMovieToDetailMovieResponse(),
                    nextKey = 1, // always 1, for initialization, reflect in actual remoteMainDataSource
                    prevKey = null //TODO paging to previous page
                )
            }
        }
    }

    override suspend fun getListTvShow(): TvShowResponse {
        TODO("Not yet implemented")
    }
}

