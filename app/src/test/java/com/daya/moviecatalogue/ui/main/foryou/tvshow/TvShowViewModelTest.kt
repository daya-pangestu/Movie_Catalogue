package com.daya.moviecatalogue.ui.main.foryou.tvshow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.daya.moviecatalogue.data.Resource
import com.daya.moviecatalogue.data.main.MainRepository
import com.daya.moviecatalogue.shared.DataDummy
import com.daya.moviecatalogue.shared.MainCoroutineRule
import com.daya.moviecatalogue.shared.getOrAwaitValue
import com.daya.moviecatalogue.shared.observeForTesting
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class TvShowViewModelTest{

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var mainRepository: MainRepository
    private lateinit var tvShowViewModel: TvShowViewModel

    private val dummyListTvShows = DataDummy.getListTvShow()

    @Before
    fun setUp() {
        mainRepository = mock()
        tvShowViewModel = TvShowViewModel(mainRepository)
    }

    @Test
    fun `TvShowViewModel#discoverTvShow should return resSucces listTvShows`() = mainCoroutineRule.testDispatcher.runBlockingTest{
        whenever(mainRepository.discoverTvShow()).thenReturn(dummyListTvShows)

        //initial value
        val actualResLoading = tvShowViewModel.discoverTvShow.getOrAwaitValue()
        Truth.assertThat(actualResLoading).isEqualTo(Resource.Loading)

        //latest value
        tvShowViewModel.discoverTvShow.observeForTesting {
            Truth.assertThat(tvShowViewModel.discoverTvShow.value).isEqualTo(Resource.Success(dummyListTvShows))
        }
        verify(mainRepository).discoverTvShow()
    }
}