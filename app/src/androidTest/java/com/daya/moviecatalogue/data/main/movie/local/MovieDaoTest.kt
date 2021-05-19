package com.daya.moviecatalogue.data.main.movie.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.daya.moviecatalogue.data.DataDummy
import com.daya.moviecatalogue.data.db.MovieCatDatabase
import com.daya.moviecatalogue.data.main.LocalDetailDataSource
import com.daya.moviecatalogue.mapToMovieEntity
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class MovieDaoTest {

    private lateinit var movieDao : MovieDao
    private lateinit var db : MovieCatDatabase
    private lateinit var localDetailDataSource : LocalDetailDataSource

    private val dummyMovie = DataDummy.getListMovie()[7]

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
    fun insertMovie_should_return_rowId() = runBlocking {
        val expected = dummyMovie.mapToMovieEntity()
        val actualRowId = movieDao.insertMovie(expected)

        Truth.assertThat(actualRowId).isEqualTo(expected.id)
    }

    @Test
    fun getDetailMovie_should_return_MovieEntity() = runBlocking {
        val expectedValue = dummyMovie.mapToMovieEntity()
        movieDao.insertMovie(expectedValue)

        val actualValue = movieDao.getMovieById(expectedValue.id)
        assertThat(actualValue).isEqualTo(expectedValue)
    }

    @Test
    fun deleteMovie_should_return_rowdeleted_count() = runBlocking {
        val expected = dummyMovie.mapToMovieEntity()
        val actualRowId = movieDao.insertMovie(expected)

        assertThat(actualRowId).isEqualTo(expected.id)

        val rowDeleted = movieDao.deleteMovie(expected)

        assertThat(rowDeleted).isEqualTo(1L)
    }

    @Test
    fun getMovies_should_return_flow_list_movieentity() = runBlocking {
        val expected = dummyMovie.mapToMovieEntity()

        movieDao.insertMovie(expected)

        movieDao.getMovies().test {
            assertThat(expected).isEqualTo(expectItem())
            expectComplete()
        }
    }

}