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
import com.daya.moviecatalogue.ui.MovieCatApplication
import com.daya.moviecatalogue.ui.detail.DetailActivity
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Shadows

@RunWith(AndroidJUnit4::class)
class MovieFragmentInstrumentedTest{
    private val dummyMovie = DataDummy.getListMovie()

    @Test
    fun `recyclerview should load all getListMovie`() {
        launchFragmentInContainer<MovieFragment>()
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movie)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                dummyMovie.size
            ),click()
        )
    }

    @Test
    fun `item recyclerview movie click should intent into detail`() {
       val scenario = launchFragmentInContainer<MovieFragment>()
        onView(withId(R.id.rv_movie)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                0
            ),click()
        )
        scenario.onFragment{
            val expectedIntent = Intent(it.context, DetailActivity::class.java)
            val app = ApplicationProvider.getApplicationContext<MovieCatApplication>()
            val actual = Shadows.shadowOf(app).nextStartedActivity
            assertThat(expectedIntent.component).isEqualTo(actual.component)
        }
    }
}