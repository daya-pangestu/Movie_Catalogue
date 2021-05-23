package com.daya.moviecatalogue.ui.main.favorite.tvshow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.daya.moviecatalogue.data.main.LocalPersistRepository
import com.daya.moviecatalogue.shared.DataDummy
import com.daya.moviecatalogue.shared.MainCoroutineRule
import com.daya.moviecatalogue.shared.getOrAwaitValue
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class TvShowFavViewModelTest{
    
    @get:Rule(order = 1)
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule(order = 2)
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var tvShowFavViewModel: TvShowFavViewModel
    private lateinit var localPersistRepository: LocalPersistRepository

    private val dummyListTvShows = DataDummy.getListTvShow()
    private val dummyFlowTvShows = flowOf(dummyListTvShows)

    @Before
    fun setUp() {
        localPersistRepository = mock()
        tvShowFavViewModel = TvShowFavViewModel(localPersistRepository)
    }

    @Test
    fun `verify localPersistRepository#getAllFavoriteTvShows get called when favoriteTvShows invoked`() = mainCoroutineRule.testDispatcher.runBlockingTest{
        whenever(localPersistRepository.getAllFavoriteTvShow()).thenReturn(dummyFlowTvShows)
        val actual = tvShowFavViewModel.favoriteTvShows.getOrAwaitValue()

        verify(localPersistRepository).getAllFavoriteTvShow()
        Truth.assertThat(actual).isEqualTo(dummyListTvShows)
    }
}