package com.daya.moviecatalogue.ui.main.favorite.movie

import androidx.paging.*
import androidx.recyclerview.widget.ListUpdateCallback
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.daya.moviecatalogue.data.main.*
import com.daya.moviecatalogue.data.main.movie.Movie
import com.daya.moviecatalogue.data.main.movie.local.MovieDao
import com.daya.moviecatalogue.data.main.movie.local.MovieEntity
import com.daya.moviecatalogue.mapToMovieEntity
import com.daya.moviecatalogue.movieDiffCallback
import com.daya.moviecatalogue.shared.DataDummy
import com.daya.moviecatalogue.shared.MainCoroutineRule
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
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
class MovieFavViewModelTest {

    @get:Rule(order = 0)
    var mainCoroutineRule = MainCoroutineRule()

    lateinit var  movieFavFavViewModel : MovieFavViewModel
    lateinit var localPersistRepository : LocalPersistRepository

    private val dummyListMovies = DataDummy.getListMovie()

    lateinit var localPersistDataSource: LocalPersistDataSource
    lateinit var localMainDataSource: LocalMainDataSource
    lateinit var localDetailDataSource: LocalDetailDataSource

    private val testDispatcher = TestCoroutineScope(mainCoroutineRule.testDispatcher)

    @Before
    fun setUp() {
        localPersistRepository = mock()
        localMainDataSource = mock()
        localDetailDataSource = mock()
        localPersistDataSource = mock()

        localPersistRepository = LocalPersistRepository(localPersistDataSource,localMainDataSource,localDetailDataSource,testDispatcher,mainCoroutineRule.testDispatcher)
        movieFavFavViewModel = MovieFavViewModel(localPersistRepository)
    }

    @ExperimentalTime
    @Test
    fun `verify_localPersistRepositorygetAllFavoriteMoviesgetcalledwhenfavoriteMoviesinvoked`() =
        mainCoroutineRule.testDispatcher.runBlockingTest {
            whenever(localPersistRepository.getAllFavoriteMovies()).thenReturn(FakePersistRepository(dummyListMovies).getAllFavoriteMovies())
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

            advanceUntilIdle()
            assertThat(differ.snapshot().items.size).isGreaterThan(0)

            job.cancel()
        }

    private val noopListCallback = object : ListUpdateCallback {
        override fun onInserted(position: Int, count: Int) {}
        override fun onRemoved(position: Int, count: Int) {}
        override fun onMoved(fromPosition: Int, toPosition: Int) {}
        override fun onChanged(position: Int, count: Int, payload: Any?) {}
    }


    class FakePersistRepository(private val listMovies : List<Movie> ) : PersistRepository {
        override fun getAllFavoriteMovies(): Flow<PagingData<Movie>> {
            return Pager(
                config = PagingConfig(
                    pageSize = 20,
                )
            ){
                fakePagingDataMovie
            }.flow
        }

        private val fakePagingDataMovie :PagingSource<Int, Movie> = object : PagingSource<Int, Movie>() {
            override fun getRefreshKey(state: PagingState<Int, Movie>): Int?= null
            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
                return LoadResult.Page(
                    data = listMovies,
                    prevKey = null,
                    nextKey = null,
                )
            }
        }

    }
}