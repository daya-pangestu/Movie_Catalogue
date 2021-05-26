package com.daya.moviecatalogue.data.main

import androidx.paging.*
import com.daya.moviecatalogue.data.main.movie.Movie
import com.daya.moviecatalogue.data.main.movie.local.MovieDao
import com.daya.moviecatalogue.data.main.movie.local.MovieEntity
import com.daya.moviecatalogue.data.main.movie.response.DetailMovieResponse
import com.daya.moviecatalogue.data.main.movie.response.MovieResponse
import com.daya.moviecatalogue.data.main.tvshow.local.TvShowDao
import com.daya.moviecatalogue.data.main.tvshow.local.TvShowEntity
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
    suspend fun getListMovies() : Movies
    suspend fun getListTvShow() : TvShows
}

@Singleton
class RemoteMainDataSource
@Inject
constructor(
   private val api : TheMovieDbApi,
) : MainDataSource<MovieResponse,TvShowResponse> {
    override suspend fun getListMovies(): MovieResponse = suspendCancellableCoroutine {continuation ->
//        val client = api.discoverMovie()
//        client.enqueue(object : Callback<MovieResponse> {
//            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
//                val body : MovieResponse = response.body() ?: return continuation.resumeWithException(Exception("body null"))
//                continuation.resume(body)
//            }
//
//            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
//                continuation.resumeWithException(t)
//            }
//        })
//
//        continuation.invokeOnCancellation {
//            client.cancel()
//        }
    }

    override suspend fun getListTvShow(): TvShowResponse =  suspendCancellableCoroutine {continuation ->
        val client = api.discoverTvShow(1)
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
class RemoteDiscoverMoviesPagingSource
@Inject
constructor(
    private val api : TheMovieDbApi
) : PagingSource<Int, DetailMovieResponse>() {

    override fun getRefreshKey(state: PagingState<Int, DetailMovieResponse>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DetailMovieResponse> {
        try {
            val nexPage = params.key ?: 1
            val response = api.discoverMovie(nexPage)

            return LoadResult.Page(
                data = response.results,
                prevKey = if (nexPage == 1) null else nexPage - 1,
                nextKey = nexPage +1
                )
        } catch (e: Exception) {
            return LoadResult.Error(e)
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
   /* override suspend fun getListMovies(): Flow<List<MovieEntity>> {
        return  movieDao.getMovies()
    }*/
     fun getListMovies(): Flow<PagingData<MovieEntity>> {
      return Pager(
            config = PagingConfig(
                pageSize = 20,
            )
        ){
           movieDao.getMoviesPaged()
       }.flow
    }

    fun getListTvShow(): Flow<List<TvShowEntity>> {
        return tvShowDao.getTvShows()
    }
}