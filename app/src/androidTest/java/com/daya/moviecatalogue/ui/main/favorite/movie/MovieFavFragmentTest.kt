package com.daya.moviecatalogue.ui.main.favorite.movie

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
import com.daya.moviecatalogue.data.main.movie.local.MovieDao
import com.daya.moviecatalogue.di.idlingresource.TestIdlingResource
import com.daya.moviecatalogue.mapToMovieEntity
import com.daya.moviecatalogue.shared.DataDummy
import com.daya.moviecatalogue.shared.RecyclerViewItemCountAssertion
import com.daya.moviecatalogue.ui.detail.DetailActivity
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import launchFragmentInHiltContainer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class MovieFavFragmentTest{

    @get:Rule(order = 0)
    var hiltAndroidRule = HiltAndroidRule(this)

    val testIdlingResource = TestIdlingResource

    @Inject
    lateinit var movieDao :MovieDao

    @Before
    fun setUp() {
        hiltAndroidRule.inject()
        IdlingRegistry.getInstance().register(testIdlingResource.get)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(testIdlingResource.get)
    }

    @Test
    fun movieFavFragment_should_display_saved_movie_from_db(){
        runBlocking {
            //add movie into database
            val expected = DataDummy.getListMovie().take(5).map { it.mapToMovieEntity() }
            val expectedRowId = expected.map { it.id.toLong() }
            //assert it
            val actualRowId = movieDao.batchInsertMovie(expected)
            assertThat(actualRowId).isEqualTo(expectedRowId)

            launchFragmentInHiltContainer<MovieFavFragment>()

            //check if rv is displayed,
            onView(withId(R.id.rv_list)).check { view, noViewFoundException ->
                noViewFoundException?.let { throw noViewFoundException }
                val recyclerView = view as RecyclerView
                assertThat(recyclerView.adapter).isNotNull()
                assertThat(recyclerView.adapter!!.itemCount).isGreaterThan(0)
            }
            //click one of the item
            init()
            onView(withId(R.id.rv_list)).perform(
                scrollToPosition<RecyclerView.ViewHolder>(
                    0
                ), click()
            )
            //make sure intent is fired
            intended(hasComponent(DetailActivity::class.java.name))
            release()
            //make sure menu item action_unfavorite is displayed
            onView(withId(R.id.action_unfavorite)).check(matches(isDisplayed()))
        }
    }
}