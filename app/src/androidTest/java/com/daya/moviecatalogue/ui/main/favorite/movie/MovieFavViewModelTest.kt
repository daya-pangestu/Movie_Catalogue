package com.daya.moviecatalogue.ui.main.favorite.movie

import androidx.paging.*
import androidx.recyclerview.widget.ListUpdateCallback
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.daya.moviecatalogue.data.main.LocalPersistRepository
import com.daya.moviecatalogue.data.main.PersistRepository
import com.daya.moviecatalogue.data.main.movie.Movie
import com.daya.moviecatalogue.data.main.movie.local.MovieDao
import com.daya.moviecatalogue.data.main.movie.local.MovieEntity
import com.daya.moviecatalogue.mapToMovieEntity
import com.daya.moviecatalogue.movieDiffCallback
import com.daya.moviecatalogue.shared.DataDummy
import com.daya.moviecatalogue.shared.MainCoroutineRule
import com.daya.moviecatalogue.shared.mapListMovieToMovieEntity
import com.daya.moviecatalogue.ui.main.MovieRecyclerViewAdapter
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import javax.inject.Inject
import kotlin.time.ExperimentalTime

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class MovieFavViewModelTests {

    @get:Rule(order = 0)
    var hiltAndroidRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
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

    @ExperimentalTime
    @Test
    fun `verify_localPersistRepositorygetAllFavoriteMoviesgetcalledwhenfavoriteMoviesinvoked`() =
        runBlocking(mainCoroutineRule.testDispatcher) {
            val differ = AsyncPagingDataDiffer<Movie>(
                diffCallback = movieDiffCallback,
                updateCallback = noopListCallback,
                mainDispatcher = mainCoroutineRule.testDispatcher,
                workerDispatcher = mainCoroutineRule.testDispatcher
            )

            val job = coroutinescope.launch {
                movieFavFavViewModel.favoriteMovies.collectLatest {
                    differ.submitData(it)
                }
            }

            assertThat(differ.snapshot()).isNotEmpty()

            job.cancel()
        }

    private val noopListCallback = object : ListUpdateCallback {
        override fun onInserted(position: Int, count: Int) {}
        override fun onRemoved(position: Int, count: Int) {}
        override fun onMoved(fromPosition: Int, toPosition: Int) {}
        override fun onChanged(position: Int, count: Int, payload: Any?) {}
    }
}