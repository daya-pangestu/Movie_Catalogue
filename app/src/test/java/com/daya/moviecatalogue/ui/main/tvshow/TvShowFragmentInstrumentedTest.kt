package com.daya.moviecatalogue.ui.main.tvshow

import android.content.Intent
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
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
class TvShowFragmentInstrumentedTest{
    private val dummyTvShow = DataDummy.getListTvShow()

    @Test
    fun `recyclerview should load all getListTvShow`() {
        launchFragmentInContainer<TvShowFragment>()
        onView(ViewMatchers.withId(R.id.rv_movie))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(ViewMatchers.withId(R.id.rv_movie)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                dummyTvShow.size
            )
        )
    }

    @Test
    fun `item recyclerview movie click should intent into detail`() {
        val scenario = launchFragmentInContainer<TvShowFragment>()
        onView(ViewMatchers.withId(R.id.rv_movie)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                dummyTvShow.size
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