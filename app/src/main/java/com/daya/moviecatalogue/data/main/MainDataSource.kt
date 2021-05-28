package com.daya.moviecatalogue.data.main

import androidx.paging.*
import com.daya.moviecatalogue.data.main.movie.local.MovieDao
import com.daya.moviecatalogue.data.main.movie.local.MovieEntity
import com.daya.moviecatalogue.data.main.movie.response.DetailMovieResponse
import com.daya.moviecatalogue.data.main.tvshow.local.TvShowDao
import com.daya.moviecatalogue.data.main.tvshow.local.TvShowEntity
import com.daya.moviecatalogue.data.main.tvshow.response.TvShowResponse
import com.daya.moviecatalogue.di.TheMovieDbApi
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
    suspend fun getListMovies() : Movies
    suspend fun getListTvShow() : TvShows
}

@Singleton
class RemoteMainDataSource
@Inject
constructor(
   private val api : TheMovieDbApi,
)   {
    fun getListMovies(): PagingSource<Int, DetailMovieResponse> {
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
                    val response = api.discoverMovieCoroutine()
                    val data = response.results

                    LoadResult.Page(
                        data = data,
                        nextKey = nextKey,
                        prevKey = null //TODO paging to previous page
                    )

                } catch (e: Exception) {
                    LoadResult.Error(e)
                }
            }
        }
    }

    suspend fun getListTvShow(): TvShowResponse =  suspendCancellableCoroutine {continuation ->
        val client = api.discoverTvShow()
        client.enqueue(object : Callback<TvShowResponse> {
            override fun onResponse(call: Call<TvShowResponse>, response: Response<TvShowResponse>) {
                val body : TvShowResponse = response.body() ?: return continuation.resumeWithException(Exception("body null"))
                continuation.resume(body)
            }

            override fun onFailure(call: Call<TvShowResponse>, t: Throwable) {
                continuation.resumeWithException(t)
            }
        })

        continuation.invokeOnCancellation {
            client.cancel()
        }
    }
}

@Singleton
class LocalMainDataSource
@Inject
constructor(
    private val movieDao: MovieDao,
    private val tvShowDao: TvShowDao
) {
     fun getListMovies(): PagingSource<Int, MovieEntity> {
      return movieDao.getMoviesPaged()
    }

    fun getListTvShow(): Flow<List<TvShowEntity>> {
        return tvShowDao.getTvShows()
    }
}