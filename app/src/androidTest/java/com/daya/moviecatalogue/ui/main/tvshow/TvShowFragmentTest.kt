package com.daya.moviecatalogue.ui.main.tvshow

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.daya.moviecatalogue.R
import com.daya.moviecatalogue.RecyclerViewItemCountAssertion
import com.daya.moviecatalogue.di.idlingresource.IdlingResources
import com.daya.moviecatalogue.di.idlingresource.TestIdlingResource
import com.daya.moviecatalogue.launchFragmentInHiltContainer
import com.daya.moviecatalogue.ui.detail.DetailActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
@LargeTest
class TvShowFragmentTest {

    @get:Rule(order = 0)
    var hiltAndroidRule = HiltAndroidRule(this)


    val testIdlingResource = TestIdlingResource

    @Before
    fun setUp() {
        hiltAndroidRule.inject()
        IdlingRegistry.getInstance().register(testIdlingResource.idlingResources)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(testIdlingResource.idlingResources)
    }

    @Test
    fun tvShow_Recyclerview_should_be_able_intent_to_detail_activity() {
        //GIVEN
        launchFragmentInHiltContainer<TvShowFragment>()

        //WHEN
        onView(withId(R.id.rv_list))
            .check(matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.rv_list))
            .check(RecyclerViewItemCountAssertion(expectedCount = 20))

        //THEN
        Intents.init()
        onView(ViewMatchers.withId(R.id.rv_list)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                20
            ), ViewActions.click()
        )
        Intents.intended(IntentMatchers.hasComponent(DetailActivity::class.java.name))
        Intents.release()
    }
}