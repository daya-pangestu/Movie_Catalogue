package com.daya.moviecatalogue.ui.main.favorite.tvshow

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.daya.moviecatalogue.data.main.PersistRepository
import com.daya.moviecatalogue.shared.DataDummy
import com.daya.moviecatalogue.shared.MainCoroutineRule
import com.daya.moviecatalogue.shared.mapListMovieToMovieEntity
import com.daya.moviecatalogue.ui.main.favorite.movie.MovieFavViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import javax.inject.Inject

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class TvShowFavFragmentTest {

    @get:Rule(order = 0)
    var hiltAndroidRule = HiltAndroidRule(this)

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    val coroutinescope = TestCoroutineScope(mainCoroutineRule.testDispatcher)

    @Inject
    lateinit var fakeLocalPersistRepository : PersistRepository

    lateinit var  movieFavFavViewModel : MovieFavViewModel

    private val dummyListMovies = DataDummy.getListMovie().mapListMovieToMovieEntity()

    @Before
    fun setUp() {
        hiltAndroidRule.inject()
        movieFavFavViewModel = MovieFavViewModel(fakeLocalPersistRepository)
    }
}