package com.daya.moviecatalogue.ui.main

import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test


class MainViewModelTest{
    private lateinit var viewModel : MainViewModel

    @Before
    fun setUp() {
        viewModel = MainViewModel()
    }

    @Test
    fun `getMovie should not null`() {
        val listMovie = viewModel.getMovie
        assertThat(listMovie).isNotNull()
    }

    @Test
    fun `getMovie should not empty`() {
        val listMovie = viewModel.getMovie
        assertThat(listMovie).isNotEmpty()
    }

    @Test
    fun `getTvShow should not null`() {
        val listTvShow = viewModel.getMovie
        assertThat(listTvShow).isNotNull()
    }

    @Test
    fun `getTvShow should not empty`() {
        val listMovie = viewModel.getMovie
        assertThat(listMovie).isNotEmpty()
    }

}