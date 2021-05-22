package com.daya.moviecatalogue.data.main

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.daya.moviecatalogue.shared.DataDummy
import com.daya.moviecatalogue.data.db.MovieCatDatabase
import com.daya.moviecatalogue.data.main.movie.local.MovieDao
import com.daya.moviecatalogue.shared.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class LocalDetailDataSourceTest{

    private lateinit var movieDao : MovieDao
    private lateinit var db : MovieCatDatabase
    private lateinit var localDetailDataSource : LocalDetailDataSource

    private val dummyMovie = DataDummy.getListMovie()[7]

    @get:Rule
    var maincoroutineRule = MainCoroutineRule()



}