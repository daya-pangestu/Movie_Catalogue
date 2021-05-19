package com.daya.moviecatalogue.ui.main.foryou.movie

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
import com.daya.moviecatalogue.di.idlingresource.TestIdlingResource
import com.daya.moviecatalogue.ui.detail.DetailActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
@LargeTest
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
    fun movie_Recyclerview_should_be_able_intent_to_detail_activity() {
        launchFragmentInHiltContainer<MovieFragment>()

        onView(withId(R.id.rv_list)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_list)).check(RecyclerViewItemCountAssertion(expectedCount = 20))

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