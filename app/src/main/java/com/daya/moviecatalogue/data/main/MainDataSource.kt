package com.daya.moviecatalogue.data.main

import androidx.paging.*
import com.daya.moviecatalogue.data.main.movie.local.MovieDao
import com.daya.moviecatalogue.data.main.movie.local.MovieEntity
import com.daya.moviecatalogue.data.main.movie.response.DetailMovieResponse
import com.daya.moviecatalogue.data.main.tvshow.local.TvShowDao
import com.daya.moviecatalogue.data.main.tvshow.local.TvShowEntity
import com.daya.moviecatalogue.data.main.tvshow.response.DetailTvShowResponse
import com.daya.moviecatalogue.data.main.tvshow.response.TvShowResponse
import com.daya.moviecatalogue.di.TheMovieDbApi
import com.daya.moviecatalogue.di.idlingresource.TestIdlingResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

interface MainDataSource<Movies,TvShows> {
    fun getListMovies() : Movies
    fun getListTvShow() : TvShows
}

@Singleton
class RemoteMainDataSource
@Inject
constructor(
   private val api : TheMovieDbApi,
) :MainDataSource<PagingSource<Int, DetailMovieResponse>,PagingSource<Int, DetailTvShowResponse>> {
   override fun getListMovies(): PagingSource<Int, DetailMovieResponse> {
       return object : PagingSource<Int, DetailMovieResponse>() {
            override fun getRefreshKey(state: PagingState<Int, DetailMovieResponse>): Int? {
                return state.anchorPosition?.let { anchorPosition ->
                    val pagePosition = state.closestPageToPosition(anchorPosition)
                    pagePosition?.prevKey?.plus(1) ?: pagePosition?.nextKey?.minus(1)
                }
            }

            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DetailMovieResponse> {
                val nextKey = params.key ?: 1
                return try {
                    TestIdlingResource.increment()
                    val response = api.discoverMovieCoroutine(nextKey)
                    val data = response.results
                    LoadResult.Page(
                        data = data,
                        nextKey = nextKey.plus(1),
                        prevKey = null //TODO paging to previous page
                    )

                } catch (e: Exception) {
                    LoadResult.Error(e)
                }finally {
                    TestIdlingResource.decrement()
                }
            }
        }
    }

    override fun getListTvShow(): PagingSource<Int, DetailTvShowResponse> {
        return object : PagingSource<Int, DetailTvShowResponse>() {
            override fun getRefreshKey(state: PagingState<Int, DetailTvShowResponse>): Int? {
                return state.anchorPosition?.let { anchorPosition ->
                    val pagePosition = state.closestPageToPosition(anchorPosition)
                    pagePosition?.prevKey?.plus(1) ?: pagePosition?.nextKey?.minus(1)
                }
            }

            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DetailTvShowResponse> {
                val nextKey = params.key ?: 1
                return try {
                    TestIdlingResource.increment()
                    val response = api.discoverTvShowCorooutine(nextKey)
                    val data = response.results
                    LoadResult.Page(
                        data = data,
                        nextKey = nextKey.plus(1),
                        prevKey = null //TODO paging to previous page
                    )

                } catch (e: Exception) {
                    LoadResult.Error(e)
                }finally {
                    if (!TestIdlingResource.get.isIdleNow) {
                        TestIdlingResource.decrement()
                    }
                }
            }
        }
    }
}

@Singleton
class LocalMainDataSource
@Inject
constructor(
    private val movieDao: MovieDao,
    private val tvShowDao: TvShowDao
) : MainDataSource<PagingSource<Int, MovieEntity>,PagingSource<Int, TvShowEntity>> {
     override fun getListMovies(): PagingSource<Int, MovieEntity> {
      return try {
          TestIdlingResource.increment()
          movieDao.getMoviesPaged()
      }finally {
          if (!TestIdlingResource.get.isIdleNow) {
              TestIdlingResource.decrement()
          }
      }
    }

    override fun getListTvShow(): PagingSource<Int, TvShowEntity> {
        return try {
            TestIdlingResource.increment()
            tvShowDao.getTvShowsPaged()
        }finally {
            if (!TestIdlingResource.get.isIdleNow) {
                TestIdlingResource.decrement()
            }
        }
    }
}