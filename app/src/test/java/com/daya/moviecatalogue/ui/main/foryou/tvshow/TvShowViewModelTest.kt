package com.daya.moviecatalogue.ui.main.foryou.tvshow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.AsyncPagingDataDiffer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.daya.moviecatalogue.data.Resource
import com.daya.moviecatalogue.data.main.RemoteMainRepository
import com.daya.moviecatalogue.data.main.movie.Movie
import com.daya.moviecatalogue.data.main.tvshow.TvShow
import com.daya.moviecatalogue.movieDiffCallback
import com.daya.moviecatalogue.shared.*
import com.daya.moviecatalogue.tvShowDiffCallback
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import javax.inject.Inject

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class TvShowViewModelTest{

    @get:Rule(order = 0)
    var hiltAndroidRule = HiltAndroidRule(this)


    @get:Rule(order = 1)
    var mainCoroutineRule = MainCoroutineRule()

    @Inject
    lateinit var remoteMainRepository: RemoteMainRepository
    private lateinit var tvShowViewModel: TvShowViewModel

    private val dummyListTvShows = DataDummy.getListTvShow()

    @Before
    fun setUp() {
        hiltAndroidRule.inject()
        tvShowViewModel = TvShowViewModel(remoteMainRepository)
    }

    @Test
    fun `TvShowViewModel#discoverTvShow should return resSucces listTvShows`() =
        runBlocking(mainCoroutineRule.testDispatcher) {
            val differ = AsyncPagingDataDiffer<TvShow>(
                diffCallback = tvShowDiffCallback,
                updateCallback = noopListCallback,
                mainDispatcher = mainCoroutineRule.testDispatcher,
                workerDispatcher = mainCoroutineRule.testDispatcher
            )

            val job = launch {
                tvShowViewModel.discoverTvShow.collectLatest {
                    differ.submitData(it)
                }
            }
            assertThat(differ.snapshot()).isNotEmpty()
            job.cancel()
        }
}