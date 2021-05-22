package com.daya.moviecatalogue.data.main.tvshow.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.daya.moviecatalogue.data.DataDummy
import com.daya.moviecatalogue.data.db.MovieCatDatabase
import com.daya.moviecatalogue.data.main.LocalDetailDataSource
import com.daya.moviecatalogue.mapToTvShowEntity
import com.google.common.truth.Truth
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class TvShowDaoTest{

    private lateinit var tvShowDao : TvShowDao
    private lateinit var db : MovieCatDatabase
    private lateinit var localDetailDataSource : LocalDetailDataSource

    private val dummyTvShow = DataDummy.getListTvShow()[7].mapToTvShowEntity()

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<HiltTestApplication>()
        db = Room.inMemoryDatabaseBuilder(context, MovieCatDatabase::class.java).build()
        tvShowDao = db.TvShowDao()
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        db.close()
    }

    @Test
    fun insertTvShow_should_return_rowId() = runBlocking {
        val actualRowId = tvShowDao.insertTvShow(dummyTvShow)

        Truth.assertThat(actualRowId).isEqualTo(dummyTvShow.id)
    }

    @Test
    fun getTvShowById_should_return_TvShowEntity() = runBlocking {
        tvShowDao.insertTvShow(dummyTvShow)

        val actualValue = tvShowDao.getTvShowById(dummyTvShow.id)
        Truth.assertThat(actualValue).isEqualTo(dummyTvShow)
    }

    @Test
    fun deleteTvShow_should_return_rowdeleted_count() = runBlocking {
        val actualRowId = tvShowDao.insertTvShow(dummyTvShow)

        Truth.assertThat(actualRowId).isEqualTo(dummyTvShow.id)

        val rowDeleted = tvShowDao.deleteTvShow(dummyTvShow)

        Truth.assertThat(rowDeleted).isEqualTo(1L)
    }

    @Test
    fun getTvShows_should_return_flow_list_movieentity() = runBlocking {

        tvShowDao.insertTvShow(dummyTvShow)

        val actual = tvShowDao.getTvShows().take(1).toList().flatten()

        Truth.assertThat(dummyTvShow).isIn(actual)
    }

}