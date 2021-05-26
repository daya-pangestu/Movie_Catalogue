package com.daya.moviecatalogue.data.main.tvshow.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.daya.moviecatalogue.shared.DataDummy
import com.daya.moviecatalogue.data.db.MovieCatDatabase
import com.daya.moviecatalogue.data.main.LocalDetailDataSource
import com.daya.moviecatalogue.mapToTvShowEntity
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
class TvShowDaoTest{

    @get:Rule
    var hiltAndroidRule = HiltAndroidRule(this)

    @Inject
    lateinit var tvShowDao : TvShowDao

    private val dummyTvShow = DataDummy.getListTvShow()[7].mapToTvShowEntity()

    @Before
    fun setUp() {
        hiltAndroidRule.inject()
    }

    @Test
    fun insertTvShow_should_return_rowId() = runBlocking {
        val actualRowId = tvShowDao.insertTvShow(dummyTvShow)

        assertThat(actualRowId).isEqualTo(dummyTvShow.id)
    }

    @Test
    fun getTvShowById_should_return_TvShowEntity() = runBlocking {
        tvShowDao.insertTvShow(dummyTvShow)

        val actualValue = tvShowDao.getTvShowById(dummyTvShow.id)
        assertThat(actualValue).isEqualTo(dummyTvShow)
    }

    @Test
    fun deleteTvShow_should_return_rowDeleted_count() = runBlocking {
        val actualRowId = tvShowDao.insertTvShow(dummyTvShow)
        assertThat(actualRowId).isEqualTo(dummyTvShow.id)

        val rowDeleted = tvShowDao.deleteTvShow(dummyTvShow)
        assertThat(rowDeleted).isEqualTo(1L)
    }

    @Test
    fun getTvShows_should_return_flow_list_moviEentity() = runBlocking {
        tvShowDao.insertTvShow(dummyTvShow)

        val actual = tvShowDao.getTvShows().take(1).toList().flatten()
        assertThat(dummyTvShow).isIn(actual)
    }
}