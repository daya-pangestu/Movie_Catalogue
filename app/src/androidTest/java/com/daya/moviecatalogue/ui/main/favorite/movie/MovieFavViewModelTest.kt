package com.daya.moviecatalogue.ui.main.favorite.movie

import androidx.paging.*
import androidx.recyclerview.widget.ListUpdateCallback
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.daya.moviecatalogue.data.main.LocalPersistRepository
import com.daya.moviecatalogue.data.main.movie.Movie
import com.daya.moviecatalogue.data.main.movie.local.MovieDao
import com.daya.moviecatalogue.data.main.movie.local.MovieEntity
import com.daya.moviecatalogue.mapToMovieEntity
import com.daya.moviecatalogue.movieDiffCallback
import com.daya.moviecatalogue.shared.DataDummy
import com.daya.moviecatalogue.shared.MainCoroutineRule
import com.google.common.truth.Truth.assertThat
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
import org.mockito.kotlin.whenever
import kotlin.time.ExperimentalTime

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class MovieFavViewModelTests {

    @get:Rule(order = 0)
    var mainCoroutineRule = MainCoroutineRule()

    lateinit var movieDao :MovieDao

    lateinit var localPersistRepository : LocalPersistRepository

    lateinit var  movieFavFavViewModel : MovieFavViewModel

    private val dummyListMovies = DataDummy.getListMovie()

    @Before
    fun setUp() {
        movieDao = mock()
        movieFavFavViewModel = MovieFavViewModel(localPersistRepository)
    }

    @ExperimentalTime
    @Test
    fun `verify_localPersistRepositorygetAllFavoriteMoviesgetcalledwhenfavoriteMoviesinvoked`() =
        mainCoroutineRule.testDispatcher.runBlockingTest {
            whenever(localPersistRepository.getAllFavoriteMovies()).thenReturn(
                Pager(
                    config = PagingConfig(
                        pageSize = 20,
                    )
                ){
                    fakePagingDataMovie
                }.flow
            )

            val differ = AsyncPagingDataDiffer<Movie>(
                diffCallback = movieDiffCallback,
                updateCallback = noopListCallback,
                mainDispatcher = mainCoroutineRule.testDispatcher,
                workerDispatcher = mainCoroutineRule.testDispatcher
            )


            val job = launch {
                movieFavFavViewModel.favoriteMovies.collectLatest {
                    differ.submitData(it)
                }
            }
            assertThat(differ.snapshot().items.size).isGreaterThan(0)

            job.cancel()
        }

    private val noopListCallback = object : ListUpdateCallback {
        override fun onInserted(position: Int, count: Int) {}
        override fun onRemoved(position: Int, count: Int) {}
        override fun onMoved(fromPosition: Int, toPosition: Int) {}
        override fun onChanged(position: Int, count: Int, payload: Any?) {}
    }

    private val fakePagingDataMovie :PagingSource<Int, Movie> = object : PagingSource<Int, Movie>() {
        override fun getRefreshKey(state: PagingState<Int, Movie>): Int?= null
        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
            return LoadResult.Page(
                data = dummyListMovies,
                prevKey = null,
                nextKey = null,
            )
        }
    }
}