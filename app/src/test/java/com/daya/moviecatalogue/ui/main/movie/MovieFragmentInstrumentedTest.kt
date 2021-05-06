package com.daya.moviecatalogue.ui.main.movie

import android.content.Intent
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.daya.moviecatalogue.R
import com.daya.moviecatalogue.data.DataDummy
import com.daya.moviecatalogue.ui.detail.DetailActivity
import com.daya.moviecatalogue.ui.util.RecyclerViewItemCountAssertion
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Shadows.shadowOf

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class MovieFragmentInstrumentedTest {

    @get:Rule
    val hiltAndroidRule = HiltAndroidRule(this)

    private val dummyMovie = DataDummy.getListMovie()

    @Test
    fun `recyclerview should load all getListMovie`() {
        launchFragmentInContainer<MovieFragment>()

        onView(withId(R.id.rv_list)).check(matches(isDisplayed()))

        onView(withId(R.id.rv_list)).check(RecyclerViewItemCountAssertion(10))

        onView(withId(R.id.rv_list)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                dummyMovie.size
            )
        )
    }

    @Test
    fun `item recyclerview movie click should intent into detail`() {
        val scenario = launchFragmentInContainer<MovieFragment>()

        onView(withId(R.id.rv_list)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                0
            ), click()
        )

        scenario.onFragment {
            val expectedIntent = Intent(it.context, DetailActivity::class.java)
            val app = ApplicationProvider.getApplicationContext<HiltTestApplication>()
            val actual = shadowOf(app).nextStartedActivity
            assertThat(expectedIntent.component).isEqualTo(actual.component)
        }
    }
}