package com.daya.moviecatalogue.ui.detail

import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.daya.moviecatalogue.R
import com.daya.moviecatalogue.shared.DataDummy
import com.daya.moviecatalogue.di.idlingresource.TestIdlingResource
import com.daya.moviecatalogue.ui.detail.DetailActivity.Companion.DETAIL_EXTRA_MOVIE
import com.daya.moviecatalogue.ui.detail.DetailActivity.Companion.DETAIL_EXTRA_TV_SHOW
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.CoreMatchers.containsString
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class DetailActivityTest{

    @get:Rule(order = 0)
    val hiltAndroidRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltAndroidRule.inject()
        IdlingRegistry.getInstance().register(TestIdlingResource.get)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().register(TestIdlingResource.get)
    }

    @Test
    fun detail_activity_should_display_detailMovie() {
        val expectedMovie = DataDummy.getListMovie()[9]

        val detailMovieIntent = Intent(ApplicationProvider.getApplicationContext(),DetailActivity::class.java)
            .putExtra(DETAIL_EXTRA_MOVIE,expectedMovie.id)
        val scenario = launchActivity<DetailActivity>(detailMovieIntent)

        onView(withId(R.id.detail_tv_title)).check(matches(withText("${expectedMovie.title}(${expectedMovie.year})")))
        onView(withId(R.id.detail_tv_desc)).check(matches(withText(containsString(expectedMovie.description))))
        onView(withId(R.id.detail_tv_release_date)).check(matches(withText(containsString(expectedMovie.release_date))))
        onView(withId(R.id.detail_tv_score)).check(matches(withText(containsString(expectedMovie.user_score.toString()))))

        scenario.close()
    }

    @Test
    fun detail_that_display_movie_shoul_able_to_save_and_delete_movie_from_favorite() {
        val expectedMovie = DataDummy.getListMovie()[9]

        val detailMovieIntent = Intent(ApplicationProvider.getApplicationContext(),DetailActivity::class.java)
            .putExtra(DETAIL_EXTRA_MOVIE,expectedMovie.id)
        val scenario = launchActivity<DetailActivity>(detailMovieIntent)

        onView(withId(R.id.action_favorite)).check(matches(isDisplayed()))
        onView(withId(R.id.action_unfavorite)).check(doesNotExist())

        onView(withId(R.id.action_favorite)).perform(click())

        onView(withId(R.id.action_favorite)).check(doesNotExist())
        onView(withId(R.id.action_unfavorite)).check(matches(isDisplayed()))

        onView(withId(R.id.action_unfavorite)).perform(click())

        onView(withId(R.id.action_favorite)).check(matches(isDisplayed()))
        onView(withId(R.id.action_unfavorite)).check(doesNotExist())

        scenario.close()
    }

    @Test
    fun detail_activity_should_display_detailTvShow() {
        val expectedTvShow = DataDummy.getListTvShow()[9]

        val detailTvShowIntent = Intent(ApplicationProvider.getApplicationContext(),DetailActivity::class.java)
            .putExtra(DETAIL_EXTRA_TV_SHOW,expectedTvShow.id)
        val scenario = launchActivity<DetailActivity>(detailTvShowIntent)

        onView(withId(R.id.detail_tv_title)).check(matches(withText(containsString("${expectedTvShow.title}(${expectedTvShow.year})"))))
        onView(withId(R.id.detail_tv_desc)).check(matches(withText(containsString(expectedTvShow.description))))
        onView(withId(R.id.detail_tv_score)).check(matches(withText(containsString(expectedTvShow.user_score.toString()))))

        scenario.close()
    }

    @Test
    fun detail_that_display_tvshow_should_able_to_save_and_delete_tvshow_from_favorite() {
        val expectedTvShow = DataDummy.getListTvShow()[9]

        val detailMovieIntent = Intent(ApplicationProvider.getApplicationContext(),DetailActivity::class.java)
            .putExtra(DETAIL_EXTRA_TV_SHOW,expectedTvShow.id)
        val scenario = launchActivity<DetailActivity>(detailMovieIntent)

        onView(withId(R.id.action_favorite)).check(matches(isDisplayed()))
        onView(withId(R.id.action_unfavorite)).check(doesNotExist())

        onView(withId(R.id.action_favorite)).perform(click())

        onView(withId(R.id.action_favorite)).check(doesNotExist())
        onView(withId(R.id.action_unfavorite)).check(matches(isDisplayed()))

        onView(withId(R.id.action_unfavorite)).perform(click())

        onView(withId(R.id.action_favorite)).check(matches(isDisplayed()))
        onView(withId(R.id.action_unfavorite)).check(doesNotExist())

        scenario.close()
    }
}