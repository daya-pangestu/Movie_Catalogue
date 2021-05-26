package com.daya.moviecatalogue.ui.main.foryou.movie

import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.daya.moviecatalogue.*
import com.daya.moviecatalogue.data.main.MainRepository
import com.daya.moviecatalogue.data.main.movie.Movie
import com.daya.moviecatalogue.di.idlingresource.TestIdlingResource
import com.daya.moviecatalogue.shared.DataDummy
import com.daya.moviecatalogue.shared.RecyclerViewItemCountAssertion
import com.daya.moviecatalogue.ui.detail.DetailActivity
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import launchFragmentInHiltContainer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class MovieFragmentTest{

    @get:Rule(order = 0)
    var hiltAndroidRule = HiltAndroidRule(this)

    val testIdlingResource = TestIdlingResource

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
    fun movieFragment_should_display_movies_from_api() {
        runBlocking {
            launchFragmentInHiltContainer<MovieFragment>()

            onView(withId(R.id.rv_list)).check { view, noViewFoundException ->
                noViewFoundException?.let { throw noViewFoundException }
                val recyclerView = view as RecyclerView
                assertThat(recyclerView.adapter).isNotNull()
                assertThat(recyclerView.adapter!!.itemCount).isGreaterThan(0)
            }
            Intents.init()
            onView(withId(R.id.rv_list)).perform(
                RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                    20
                ), click()
            )
            intended(hasComponent(DetailActivity::class.java.name))
            Intents.release()
        }

    }


    val fakeDiscoverMoviePaging = object : PagingSource<Int, Movie>(){
        override fun getRefreshKey(state: PagingState<Int, Movie>): Int? =null
        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
            return LoadResult.Page(
                data = DataDummy.getListMovie(),
                prevKey = null,
                nextKey = null
            )
        }
    }


}