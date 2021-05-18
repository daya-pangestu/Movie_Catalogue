package com.daya.moviecatalogue.data.main

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.daya.moviecatalogue.data.DataDummy
import com.daya.moviecatalogue.data.db.MovieCatDatabase
import com.daya.moviecatalogue.data.main.movie.local.MovieDao
import com.daya.moviecatalogue.mapToMovieEntity
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltTestApplication
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class LocalPersistDataSourceTest {
    private lateinit var movieDao : MovieDao
    private lateinit var db : MovieCatDatabase

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<HiltTestApplication>()
        db = Room.inMemoryDatabaseBuilder(context, MovieCatDatabase::class.java).build()
        movieDao = db.movieDao()
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeMovieAndReadInList() {
        val movie = DataDummy.getListMovie()[9].mapToMovieEntity()

        movieDao.insertMovie(movie)
        val byId = movieDao.getMovieByIdTesting(movie.id)

       assertThat(byId).isEqualTo(movie)
    }

}