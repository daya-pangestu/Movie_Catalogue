package com.daya.moviecatalogue.data.main.tvshow.local

import androidx.paging.PagingSource
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.daya.moviecatalogue.shared.DataDummy
import com.daya.moviecatalogue.data.db.MovieCatDatabase
import com.daya.moviecatalogue.data.main.LocalDetailDataSource
import com.daya.moviecatalogue.mapToTvShowEntity
import com.daya.moviecatalogue.shared.MainCoroutineRule
import com.daya.moviecatalogue.shared.mapListTvShowToTvShowEntity
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

    @get:Rule(order = 0)
    var hiltAndroidRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var mainCoroutineRule = MainCoroutineRule()

    @Inject
    lateinit var tvShowDao : TvShowDao


    private val dummyListMovie =DataDummy.getListTvShow()
    private val dummyTvShow = dummyListMovie[7].mapToTvShowEntity()

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
    fun getTvShowById_should_return_tvShowEntity() = runBlocking {
        tvShowDao.insertTvShow(dummyTvShow)

        val actualValue = tvShowDao.getTvShowById(dummyTvShow.id)
        Truth.assertThat(actualValue).isEqualTo(dummyTvShow)
    }

    @Test
    fun deleteTvShow_should_return_rowdeleted_count() = runBlocking {
        val actualRowId = tvShowDao.insertTvShow(dummyTvShow)

        assertThat(actualRowId).isEqualTo(dummyTvShow.id)

        val rowDeleted = tvShowDao.deleteTvShow(dummyTvShow)

        assertThat(rowDeleted).isEqualTo(1L)
    }

    @Test
    fun getTvShows_should_contain_tvShowEntity() = runBlocking {

        tvShowDao.insertTvShow(dummyTvShow)

        val actual = tvShowDao.getTvShows().take(1).toList().flatten()

        assertThat(dummyTvShow).isIn(actual)
    }

    @Test
    fun getTvShowsPaged_should_not_null() =
        runBlocking(mainCoroutineRule.testDispatcher) {
            val rowSavedId =
                tvShowDao.batchInsertTvShow(dummyListMovie.mapListTvShowToTvShowEntity())
            assertThat(rowSavedId.size).isEqualTo(dummyListMovie.size)
            val pagingSource = tvShowDao.getTvShowsPaged()

            val actual = pagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = 20,
                    placeholdersEnabled = false
                )
            )
            assertThat((actual as PagingSource.LoadResult.Page).data.size).isEqualTo(dummyListMovie.size)
        }
}