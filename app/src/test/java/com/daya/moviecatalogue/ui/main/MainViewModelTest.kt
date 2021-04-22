package com.daya.moviecatalogue.ui.main

import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test


class MainViewModelTest{
    private lateinit var viewmodel : MainViewModel

    @Before
    fun setUp() {
        viewmodel = MainViewModel()
    }

    @Test
    fun `getMovie should not null`() {
        val listMovie = viewmodel.getMovie
        assertThat(listMovie).isNotNull()
    }

    @Test
    fun `getMovie should not empty`() {
        val listMovie = viewmodel.getMovie
        assertThat(listMovie).isNotEmpty()
    }

    @Test
    fun `getTvShow should not null`() {
        val listTvShow = viewmodel.getMovie
        assertThat(listTvShow).isNotNull()
    }

    @Test
    fun `getTvShow should not empty`() {
        val listMovie = viewmodel.getMovie
        assertThat(listMovie).isNotEmpty()
    }

}