package com.daya.moviecatalogue.ui.main.movie

import androidx.fragment.app.testing.launchFragment
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.daya.moviecatalogue.R
import com.daya.moviecatalogue.RecyclerViewItemCountAssertion
import com.daya.moviecatalogue.data.DataDummy
import com.daya.moviecatalogue.data.Resource
import com.daya.moviecatalogue.data.main.movie.Movie
import com.daya.moviecatalogue.di.idlingresource.DebugIdlingResource
import com.daya.moviecatalogue.launchFragmentInHiltContainer
import com.daya.moviecatalogue.ui.main.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.core.IsNot.not
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class MovieFragmentTest{

    @get:Rule(order = 0)
    var hiltAndroidRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltAndroidRule.inject()
     //   IdlingRegistry.getInstance().register(idlingResources.idlingResources)
    }

    @After
    fun tearDown() {
    //    IdlingRegistry.getInstance().unregister(idlingResources.idlingResources)
    }

    @Test
    fun movie() {
        launchFragmentInHiltContainer<MovieFragment> {
            onView(withId(R.id.progress_circular)).check(matches(not(isDisplayed())))
        }

//
//            onView(withId(R.id.rv_list)).check(RecyclerViewItemCountAssertion(expectedCount = 20))
//
//            onView(ViewMatchers.withId(R.id.rv_list)).perform(
//                RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
//                    20
//                ), ViewActions.click()
//            )

    }
}