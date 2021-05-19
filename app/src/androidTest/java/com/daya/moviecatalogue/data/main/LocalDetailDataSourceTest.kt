package com.daya.moviecatalogue.data.main

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.daya.moviecatalogue.data.DataDummy
import com.daya.moviecatalogue.data.db.MovieCatDatabase
import com.daya.moviecatalogue.data.main.movie.local.MovieDao
import com.daya.moviecatalogue.mapToMovieEntity
import com.daya.moviecatalogue.util.MainCoroutineRule
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

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