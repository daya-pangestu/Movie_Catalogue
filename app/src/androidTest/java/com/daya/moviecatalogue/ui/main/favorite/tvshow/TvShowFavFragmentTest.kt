package com.daya.moviecatalogue.ui.main.favorite.tvshow

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.*
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.daya.moviecatalogue.R
import com.daya.moviecatalogue.data.db.MovieCatDatabase
import com.daya.moviecatalogue.data.main.PersistRepository
import com.daya.moviecatalogue.data.main.movie.local.MovieDao
import com.daya.moviecatalogue.data.main.tvshow.local.TvShowDao
import com.daya.moviecatalogue.di.idlingresource.TestIdlingResource
import com.daya.moviecatalogue.mapToMovieEntity
import com.daya.moviecatalogue.mapToTvShowEntity
import com.daya.moviecatalogue.shared.DataDummy
import com.daya.moviecatalogue.shared.MainCoroutineRule
import com.daya.moviecatalogue.shared.mapListMovieToMovieEntity
import com.daya.moviecatalogue.ui.detail.DetailActivity
import com.daya.moviecatalogue.ui.main.favorite.movie.MovieFavFragment
import com.daya.moviecatalogue.ui.main.favorite.movie.MovieFavViewModel
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineScope
import launchFragmentInHiltContainer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class TvShowFavFragmentTest {

    @get:Rule(order = 0)
    var hiltAndroidRule = HiltAndroidRule(this)

    val testIdlingResource = TestIdlingResource

    @Inject
    lateinit var db : MovieCatDatabase

    lateinit var tvShowDao : TvShowDao

    @Before
    fun setUp() {
        hiltAndroidRule.inject()
        tvShowDao = db.TvShowDao()
        IdlingRegistry.getInstance().register(testIdlingResource.get)
    }

    @After
    fun tearDown() {
        db.close()
        IdlingRegistry.getInstance().unregister(testIdlingResource.get)
    }

    @Test
    fun tvshowFavFragment_should_display_saved_tvshow_from_db(){
        runBlocking {
            //add movie into database
            val expected = DataDummy.getListTvShow().take(5).map { it.mapToTvShowEntity() }
            val expectedRowId = expected.map { it.id.toLong() }
            //assert it
            val actualRowId = tvShowDao.batchInsertTvShow(expected)
            assertThat(actualRowId).isEqualTo(expectedRowId)

            launchFragmentInHiltContainer<TvShowFavFragment>()

            onView(ViewMatchers.withId(R.id.rv_list))
                .check(matches(isDisplayed()))

            onView(withId(R.id.rv_list)).check { view, noViewFoundException ->
                if (noViewFoundException != null) throw noViewFoundException
                val recyclerView = view as RecyclerView
                assertThat(recyclerView.adapter!!.itemCount).isGreaterThan(0)
            }
            init()
            onView(withId(R.id.rv_list)).perform(
                scrollToPosition<RecyclerView.ViewHolder>(
                    0
                ), click()
            )
            intended(hasComponent(DetailActivity::class.java.name))
            release()
            onView(withId(R.id.action_unfavorite))
                .check(matches(isDisplayed()))
        }
    }
}