package com.daya.moviecatalogue.ui.detail

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.activityScenarioRule
import com.daya.moviecatalogue.R
import com.daya.moviecatalogue.data.DataDummy
import com.daya.moviecatalogue.di.idlingresource.DebugIdlingResource
import com.daya.moviecatalogue.ui.detail.DetailActivity.Companion.DETAIL_EXTRA_MOVIE
import com.daya.moviecatalogue.ui.detail.DetailActivity.Companion.DETAIL_EXTRA_TV_SHOW
import com.daya.moviecatalogue.ui.splash.SplashActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.stub

@HiltAndroidTest
class DetailActivityTest{

    @get:Rule(order = 0)
    val hiltAndroidRule = HiltAndroidRule(this)

//    @get:Rule
//    val activityRule = activityScenarioRule<DetailActivity>()


    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(DebugIdlingResource.idlingResources)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().register(DebugIdlingResource.idlingResources)
    }


    @Test
    fun detail_activity_should_display_detailMovie() {
        val expectedMovie = DataDummy.getListMovie()[9]

        val detailMovieIntent = Intent()
            .putExtra(DETAIL_EXTRA_MOVIE,expectedMovie.id)
        val scenario = launchActivity<DetailActivity>(detailMovieIntent)

        onView(withId(R.id.detail_tv_title)).check(matches(withText(expectedMovie.title)))


        scenario.close()
    }

    @Test
    fun detail_activity_should_display_detailTvShow() {
        val expectedTvShow = DataDummy.getListTvShow()[9]

        val detailMovieIntent = Intent()
            .putExtra(DETAIL_EXTRA_TV_SHOW,expectedTvShow.id)
        val scenario = launchActivity<DetailActivity>(detailMovieIntent)

        onView(withId(R.id.detail_tv_title)).check(matches(withText(expectedTvShow.title)))

        scenario.close()
    }

}