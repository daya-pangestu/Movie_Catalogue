package com.daya.moviecatalogue.data.main

import com.daya.moviecatalogue.data.main.movie.response.MovieResponse
import com.daya.moviecatalogue.data.main.tvshow.response.TvShowResponse
import com.daya.moviecatalogue.di.TheMovieDbApi
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

interface MainDataSource {
    suspend fun getListMovies() : MovieResponse
    suspend fun getListTvShow() : TvShowResponse
}

@Singleton
class RemoteMainDataSource
@Inject
constructor(
   private val api : TheMovieDbApi,
) : MainDataSource {
    override suspend fun getListMovies(): MovieResponse = suspendCancellableCoroutine {continuation ->
        val client = api.discoverMovie()
        client.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                val body : MovieResponse = response.body() ?: return continuation.resumeWithException(Exception("body null"))
                continuation.resume(body)
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                continuation.resumeWithException(t)
            }
        })

        continuation.invokeOnCancellation {
            client.cancel()
        }
    }

    override suspend fun getListTvShow(): TvShowResponse =  suspendCancellableCoroutine {continuation ->
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