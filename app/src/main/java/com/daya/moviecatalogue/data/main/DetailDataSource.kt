package com.daya.moviecatalogue.data.main

import com.daya.moviecatalogue.data.main.movie.Movie
import com.daya.moviecatalogue.data.main.movie.response.DetailMovie
import com.daya.moviecatalogue.data.main.tvshow.TvShow
import com.daya.moviecatalogue.data.main.tvshow.response.DetailTvShow
import com.daya.moviecatalogue.di.TheMovieDbApi
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

interface DetailDataSource {
    suspend fun getDetailTvShow(tvShowId : Int) : DetailTvShow
    suspend fun getDetailMovie(movieId : Int) : DetailMovie
}

@Singleton
class RemoteDetailDataSource
@Inject
constructor(
    private val api: TheMovieDbApi
) : DetailDataSource {
    override suspend  fun getDetailTvShow(tvShowId: Int): DetailTvShow = suspendCancellableCoroutine {continuation ->
        val client = api.getDetailTvShow(tvShowId)

        client.enqueue(object : Callback<DetailTvShow>{
            override fun onResponse(call: Call<DetailTvShow>, response: Response<DetailTvShow>) {
                val body = response.body() ?: return continuation.resumeWithException(Exception("response body null"))
                continuation.resume(body)
            }

            override fun onFailure(call: Call<DetailTvShow>, t: Throwable) {
                continuation.resumeWithException(t)
            }
        })

        continuation.invokeOnCancellation {
            client.cancel()
        }
    }

    override suspend  fun getDetailMovie(movieId: Int): DetailMovie = suspendCancellableCoroutine { continuation ->
        val client = api.getDetailMovie(movieId)

        client.enqueue(object : Callback<DetailMovie>{
            override fun onResponse(call: Call<DetailMovie>, response: Response<DetailMovie>) {
                val body = response.body() ?: return continuation.resumeWithException(Exception("response body null"))
                continuation.resume(body)
            }

            override fun onFailure(call: Call<DetailMovie>, t: Throwable) {
                continuation.resumeWithException(t)
            }
        })

        continuation.invokeOnCancellation {
            client.cancel()
        }
    }
}
