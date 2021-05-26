package com.daya.moviecatalogue.data.main

import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.daya.moviecatalogue.mapToMovieEntity
import com.daya.moviecatalogue.mapToTvShowEntity
import com.daya.moviecatalogue.shared.DataDummy
import com.daya.moviecatalogue.shared.MainCoroutineRule
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.math.exp
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class LocalPersistRepositoryTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val testCoroutineScope = TestCoroutineScope(mainCoroutineRule.testDispatcher)

    private lateinit var localPersistDataSource: LocalPersistDataSource
    private lateinit var localMainDataSource: LocalMainDataSource
    private lateinit var localDetailDataSource: LocalDetailDataSource
    private lateinit var localPersistRepository: LocalPersistRepository

    private val dummyFavoriteMovies = DataDummy.getListMovie()
    private val dummyFlowFavoriteMovies = flowOf(dummyFavoriteMovies)

    private val dummyFavoriteTvShows = DataDummy.getListTvShow()
    private val dummyFlowFavoriteTvShows = flowOf(dummyFavoriteTvShows)

    @Before
    fun setUp() {
        localPersistDataSource = mock()
        localMainDataSource = mock()
        localDetailDataSource = mock()

        localPersistRepository = LocalPersistRepository(
            localPersistDataSource,
            localMainDataSource,
            localDetailDataSource,
            testCoroutineScope,
            mainCoroutineRule.testDispatcher
        )
    }

//    @Test
//    fun `LocalPersistRepository#getAllFavoriteMovies`() =
//        mainCoroutineRule.testDispatcher.runBlockingTest {
//            whenever(localMainDataSource.getListMovies()).thenReturn(flowOf(dummyFavoriteMovies.map { it.mapToMovieEntity() }))
//
//            localPersistRepository.getAllFavoriteMovies().test {
//                assertThat(dummyFavoriteMovies).isEqualTo(expectItem())
//                expectComplete()
//            }
//        }

    @Test
    fun `LocalPersistRepository#getAllFavoriteTvShow`() =
        mainCoroutineRule.testDispatcher.runBlockingTest {
            whenever(localMainDataSource.getListTvShow()).thenReturn(flowOf(dummyFavoriteTvShows.map { it.mapToTvShowEntity() }))

            localPersistRepository.getAllFavoriteTvShow().test {
                assertThat(dummyFavoriteTvShows).isEqualTo(expectItem())
                expectComplete()
            }
        }

    @Test
    fun `LocalPersistRepository#addMovieToFavorite`() =
        mainCoroutineRule.testDispatcher.runBlockingTest {
            val expected = dummyFavoriteMovies[9]
            val expectedRowId = expected.id

            whenever(localPersistDataSource.addMovieToFavorite(expected.mapToMovieEntity())).thenReturn(
                expectedRowId.toLong()
            )

            val actualRowId = localPersistRepository.addMovieToFavorite(expected)
            assertThat(actualRowId).isEqualTo(expectedRowId)

            verify(localPersistDataSource).addMovieToFavorite(expected.mapToMovieEntity())
        }

    @Test
    fun `LocalPersistRepository#addTvShowToFavorite`() =
        mainCoroutineRule.testDispatcher.runBlockingTest {
            val expected = dummyFavoriteTvShows[9]
            val expectedRowId = expected.id

            whenever(localPersistDataSource.addTvShowToFavorte(expected.mapToTvShowEntity())).thenReturn(
                expectedRowId.toLong()
            )

            val actualRowId = localPersistRepository.addTvShowToFavorite(expected)
            assertThat(actualRowId).isEqualTo(expectedRowId)

            verify(localPersistDataSource).addTvShowToFavorte(expected.mapToTvShowEntity())
        }

    @Test
    fun `LocalPersistRepository#deleteMovieFromFavorite`() =
        mainCoroutineRule.testDispatcher.runBlockingTest {
            val expected = dummyFavoriteMovies[9]
            val expectedRowDeletedCount = 1

            whenever(localPersistDataSource.deleteMovieFromFavorite(expected.mapToMovieEntity())).thenReturn(
                expectedRowDeletedCount
            )

            val actualDeletedCount = localPersistRepository.deleteMovieFromFavorite(expected)
            assertThat(actualDeletedCount).isEqualTo(expectedRowDeletedCount)

            verify(localPersistDataSource).deleteMovieFromFavorite(expected.mapToMovieEntity())
        }

    @Test
    fun `LocalPersistRepository#deleteTvShowFromFavorite`() =
        mainCoroutineRule.testDispatcher.runBlockingTest {
            val expected = dummyFavoriteTvShows[9]
            val expectedRowDeletedCount = 1

            whenever(localPersistDataSource.deleteTvShowFromFavorite(expected.mapToTvShowEntity())).thenReturn(
                expectedRowDeletedCount
            )

            val actualDeletedCount = localPersistRepository.deleteTvShowFromFavorite(expected)
            assertThat(actualDeletedCount).isEqualTo(expectedRowDeletedCount)

            verify(localPersistDataSource).deleteTvShowFromFavorite(expected.mapToTvShowEntity())
        }

    @Test
    fun `LocalPersistRepository#isFavorite should return true from movie`() =
        mainCoroutineRule.testDispatcher.runBlockingTest {
            val expected = dummyFavoriteMovies[9].mapToMovieEntity()
            val expectedId = expected.id
            whenever(localDetailDataSource.getDetailMovie(expectedId)).thenReturn(expected)
            whenever(localDetailDataSource.getDetailTvShow(expectedId)).thenReturn(null)

            val actual = localPersistRepository.isFavorite(expectedId)
            assertThat(actual).isEqualTo(true)

            verify(localDetailDataSource).getDetailMovie(expectedId)
            verify(localDetailDataSource).getDetailTvShow(expectedId)
        }

    @Test
    fun `LocalPersistRepository#isFavorite should return true from tvShow`() =
        mainCoroutineRule.testDispatcher.runBlockingTest {
            val expected = dummyFavoriteTvShows[9].mapToTvShowEntity()
            val expectedId = dummyFavoriteTvShows[9].id

            whenever(localDetailDataSource.getDetailTvShow(expectedId)).thenReturn(expected)
            whenever(localDetailDataSource.getDetailMovie(expectedId)).thenReturn(null)

            val actual = localPersistRepository.isFavorite(expectedId)
            assertThat(actual).isEqualTo(true)

            verify(localDetailDataSource).getDetailMovie(expectedId)
            verify(localDetailDataSource).getDetailTvShow(expectedId)
        }

    @Test
    fun `LocalPersistRepository#isFavorite should return false from both movie and tvShow`() =
        mainCoroutineRule.testDispatcher.runBlockingTest {
            val expectedId = 15 // non-existed id

            whenever(localDetailDataSource.getDetailTvShow(expectedId)).thenReturn(null)
            whenever(localDetailDataSource.getDetailMovie(expectedId)).thenReturn(null)

            val actual = localPersistRepository.isFavorite(expectedId)
            assertThat(actual).isEqualTo(false)

            verify(localDetailDataSource).getDetailMovie(expectedId)
            verify(localDetailDataSource).getDetailTvShow(expectedId)
        }
}
