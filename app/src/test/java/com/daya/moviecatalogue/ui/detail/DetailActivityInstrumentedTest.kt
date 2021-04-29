package com.daya.moviecatalogue.ui.detail

import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.daya.moviecatalogue.R
import com.daya.moviecatalogue.data.DataDummy
import com.daya.moviecatalogue.ui.detail.DetailActivity.Companion.DETAIL_EXTRA_MOVIE
import com.daya.moviecatalogue.ui.detail.DetailActivity.Companion.DETAIL_EXTRA_TV_SHOW
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DetailActivityInstrumentedTest {

    private val dummyMovie = DataDummy.getListMovie()[8]
    private val dummyTvSow = DataDummy.getListTvShow()[4]

    @Test
    fun `detail should display movie`() {
        val movieIntent = Intent(ApplicationProvider.getApplicationContext(), DetailActivity::class.java).apply {
            putExtra(DETAIL_EXTRA_MOVIE, dummyMovie.title)
        }

        launchActivity<DetailActivity>(movieIntent)
        onView(withId(R.id.detail_tv_title)).check(matches(withText("${dummyMovie.title}(${dummyMovie.year})")))
        onView(withId(R.id.detail_tv_desc)).check(matches(withText(dummyMovie.description)))
        onView(withId(R.id.detail_tv_release_date)).check(matches(withText(dummyMovie.release_date)))
        onView(withId(R.id.detail_tv_genre)).check(matches(withText("${dummyMovie.rate} | ${dummyMovie.genre}")))
        onView(withId(R.id.detail_tv_score)).check(matches(withText(dummyMovie.user_score.toString())))

    }

    @Test
    fun `detail should display tvShow`() {

        val tvShowIntent = Intent(
                ApplicationProvider.getApplicationContext(),
                DetailActivity::class.java
        ).apply {
            putExtra(DETAIL_EXTRA_TV_SHOW, dummyTvSow.title)
        }
        launchActivity<DetailActivity>(tvShowIntent)
        onView(withId(R.id.detail_tv_title)).check(matches(withText("${dummyTvSow.title}(${dummyTvSow.year})")))
        onView(withId(R.id.detail_tv_desc)).check(matches(withText(dummyTvSow.description)))
        onView(withId(R.id.detail_tv_release_date)).check(matches(withText("")))
        onView(withId(R.id.detail_tv_genre)).check(matches(withText("${dummyTvSow.rate} | ${dummyTvSow.genre}")))
        onView(withId(R.id.detail_tv_score)).check(matches(withText(dummyTvSow.user_score.toString())))
    }
}
