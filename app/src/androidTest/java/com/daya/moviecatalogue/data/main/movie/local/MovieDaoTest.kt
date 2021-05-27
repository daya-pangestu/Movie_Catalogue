package com.daya.moviecatalogue.data.main.movie.local

import androidx.paging.PagingSource
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.daya.moviecatalogue.shared.DataDummy
import com.daya.moviecatalogue.data.db.MovieCatDatabase
import com.daya.moviecatalogue.data.main.LocalDetailDataSource
import com.daya.moviecatalogue.mapToMovieEntity
import com.daya.moviecatalogue.shared.MainCoroutineRule
import com.daya.moviecatalogue.shared.mapListMovieToMovieEntity
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class MovieDaoTest {

    @get:Rule
    var hiltAndroidRule = HiltAndroidRule(this)

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Inject
    lateinit var movieDao: MovieDao

    private val dummyListMovie =DataDummy.getListMovie()
    private val dummyMovie = dummyListMovie[7]

    @Before
    fun setUp() {
        hiltAndroidRule.inject()
    }

    @Test
    fun insertMovie_should_return_rowId() = runBlocking {
        val expected = dummyMovie.mapToMovieEntity()
        val actualRowId = movieDao.insertMovie(expected)

        assertThat(actualRowId).isEqualTo(expected.id)
    }

    @Test
    fun getDetailMovieById_should_return_MovieEntity() = runBlocking {
        val expectedValue = dummyMovie.mapToMovieEntity()
        movieDao.insertMovie(expectedValue)

        val actualValue = movieDao.getMovieById(expectedValue.id)
        assertThat(actualValue).isEqualTo(expectedValue)
    }

    @Test
    fun deleteMovie_should_return_rowDeleted_count() = runBlocking {
        val expected = dummyMovie.mapToMovieEntity()
        val actualRowId = movieDao.insertMovie(expected)

        assertThat(actualRowId).isEqualTo(expected.id)

        val rowDeleted = movieDao.deleteMovie(expected)
        assertThat(rowDeleted).isEqualTo(1L)
    }

    @Test
    fun getMovies_should_return_flow_list_movieEntity() = runBlocking {
        val expected = dummyMovie.mapToMovieEntity()

        movieDao.insertMovie(expected)

        val actual = movieDao.getMovies().take(1).toList().flatten()
        assertThat(expected).isIn(actual)
    }

    @Test
    fun getMoviesPage_should_return_paging_data_movie() = runBlocking(mainCoroutineRule.testDispatcher) {
        val rowSavedId = movieDao.batchInsertMovie(dummyListMovie.mapListMovieToMovieEntity())
        assertThat(rowSavedId.size).isEqualTo(dummyListMovie.size)
        val pagingSource = movieDao.getMoviesPaged()

        val actual = pagingSource.load(
            PagingSource.LoadParams.Refresh(
            key = null,
            loadSize = 20,
            placeholdersEnabled = false
        ))
        assertThat((actual as PagingSource.LoadResult.Page).data.size).isEqualTo(dummyListMovie.size)
    }

}