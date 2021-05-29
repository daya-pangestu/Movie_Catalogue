package com.daya.moviecatalogue.shared

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ListUpdateCallback
import com.daya.moviecatalogue.*
import com.daya.moviecatalogue.data.main.movie.Movie
import com.daya.moviecatalogue.data.main.movie.local.MovieEntity
import com.daya.moviecatalogue.data.main.tvshow.TvShow
import com.daya.moviecatalogue.data.main.tvshow.local.TvShowEntity
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

/* Copyright 2019 Google LLC.
   SPDX-License-Identifier: Apache-2.0 */
fun <T> LiveData<T>.getOrAwaitValue(
    time: Long = 2,
    timeUnit: TimeUnit = TimeUnit.SECONDS
): T {
    var data: T? = null
    val latch = CountDownLatch(1)
    val observer = object : Observer<T> {
        override fun onChanged(o: T?) {
            data = o
            latch.countDown()
            this@getOrAwaitValue.removeObserver(this)
        }
    }

    this.observeForever(observer)

    // Don't wait indefinitely if the LiveData is not set.
    if (!latch.await(time, timeUnit)) {
        throw TimeoutException("LiveData value was never set.")
    }

    @Suppress("UNCHECKED_CAST")
    return data as T
}

fun <T> LiveData<T>.observeForTesting(block: () -> Unit) {
    val observer = Observer<T> { }
    try {
        observeForever(observer)
        block()
    } finally {
        removeObserver(observer)
    }
}

fun List<Movie>.mapListMovieToMovieEntity() = this.map { it.mapToMovieEntity() }
fun List<MovieEntity>.mapListMovieEntityToMovie() = this.map { it.mapToMovie() }
fun List<Movie>.mapListMovieToDetailMovieResponse() = this.map { it.mapToDetailMovieResponse() }

fun List<TvShow>.mapListTvShowToTvShowEntity() = this.map { it.mapToTvShowEntity() }
fun List<TvShowEntity>.mapListTvShowEntityToTvShow() = this.map { it.mapToTvShow() }
fun List<TvShow>.mapListTvShowToDetailTvShowResponse() = this.map{it.mapToDetailTvShowResponse()}

val noopListCallback = object : ListUpdateCallback {
    override fun onInserted(position: Int, count: Int) {}
    override fun onRemoved(position: Int, count: Int) {}
    override fun onMoved(fromPosition: Int, toPosition: Int) {}
    override fun onChanged(position: Int, count: Int, payload: Any?) {}
}