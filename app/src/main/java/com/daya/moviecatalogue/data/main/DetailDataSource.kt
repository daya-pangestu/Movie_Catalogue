package com.daya.moviecatalogue.data.main

import com.daya.moviecatalogue.data.main.movie.local.MovieDao
import com.daya.moviecatalogue.data.main.movie.local.MovieEntity
import com.daya.moviecatalogue.data.main.movie.response.DetailMovieResponse
import com.daya.moviecatalogue.data.main.tvshow.local.TvShowDao
import com.daya.moviecatalogue.data.main.tvshow.local.TvShowEntity
import com.daya.moviecatalogue.data.main.tvshow.response.DetailTvShowResponse
import com.daya.moviecatalogue.di.TheMovieDbApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

interface DetailDataSource<Movie,TvShow> {
    suspend fun getDetailMovie(movieId : Int) : Movie
    suspend fun getDetailTvShow(tvShowId : Int) : TvShow
}

@Singleton
class RemoteDetailDataSource
@Inject
constructor(
    private val api: TheMovieDbApi
) : DetailDataSource<DetailMovieResponse,DetailTvShowResponse> {

    override suspend  fun getDetailMovie(movieId: Int): DetailMovieResponse = suspendCancellableCoroutine { continuation ->
        val client = api.getDetailMovie(movieId)

        client.enqueue(object : Callback<DetailMovieResponse>{
            override fun onResponse(call: Call<DetailMovieResponse>, response: Response<DetailMovieResponse>) {
                val body = response.body() ?: return continuation.resumeWithException(Exception("response body null"))
                continuation.resume(body)
            }

            override fun onFailure(call: Call<DetailMovieResponse>, t: Throwable) {
                continuation.resumeWithException(t)
            }
        })

        continuation.invokeOnCancellation {
            client.cancel()
        }
    }

    override suspend  fun getDetailTvShow(tvShowId: Int): DetailTvShowResponse = suspendCancellableCoroutine { continuation ->
        val client = api.getDetailTvShow(tvShowId)

        client.enqueue(object : Callback<DetailTvShowResponse>{
            override fun onResponse(call: Call<DetailTvShowResponse>, response: Response<DetailTvShowResponse>) {
                val body = response.body() ?: return continuation.resumeWithException(Exception("response body null"))
                continuation.resume(body)
            }

            override fun onFailure(call: Call<DetailTvShowResponse>, t: Throwable) {
                continuation.resumeWithException(t)
            }
        })

        continuation.invokeOnCancellation {
            client.cancel()
        }
    }
}

@Singleton
class LocalDetailDataSource
@Inject
constructor(
    private val movieDao: MovieDao,
    private val tvShowDao: TvShowDao
) : DetailDataSource<Flow<MovieEntity?>,Flow<TvShowEntity?>> {

    override suspend fun getDetailMovie(movieId: Int): Flow<MovieEntity?> {
        return movieDao.getMovieById(movieId)
    }

    override suspend fun getDetailTvShow(tvShowId: Int): Flow<TvShowEntity?> {
        return tvShowDao.getTvShowById(tvShowId)
    }

}

