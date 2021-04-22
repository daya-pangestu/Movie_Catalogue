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
import com.daya.moviecatalogue.data.movie.Movie
import com.daya.moviecatalogue.data.tvshow.TvShow
import com.daya.moviecatalogue.ui.detail.DetailActivity.Companion.DETAIL_EXTRA_MOVIE
import com.daya.moviecatalogue.ui.detail.DetailActivity.Companion.DETAIL_EXTRA_TV_SHOW
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DetailActivityTest {
    @Test
    fun `detail should display movie`() {
        val movie = Movie(
            title = "sicario",
            description = "this is really long text that need to repeated but im running out idea for more text to write, but as a wise man always say, doing something was better than nothing",
            genre = "action,drama,comedy",
            rate = "R",
            release_date = "19/09/1997",
            year = 1997,
            user_score = 89
        )

        val movieIntent = Intent(ApplicationProvider.getApplicationContext(),DetailActivity::class.java).apply {
            putExtra(DETAIL_EXTRA_MOVIE,movie)
        }

        launchActivity<DetailActivity>(movieIntent)
        onView(withId(R.id.detail_tv_title)).check(matches(withText("${movie.title}(${movie.year})")))
        onView(withId(R.id.detail_tv_desc)).check(matches(withText(movie.description)))
        onView(withId(R.id.detail_tv_release_date)).check(matches(withText(movie.release_date)))
        onView(withId(R.id.detail_tv_genre)).check(matches(withText("${movie.rate} | ${movie.genre}")))
        onView(withId(R.id.detail_tv_score)).check(matches(withText(movie.user_score.toString())))

    }

    @Test
    fun `detail should display tvShow`() {
        val tvShow = TvShow(
            title = "sicario",
            description = "this is really long text that need to repeated but im running out idea for more text to write, but as a wise man always say, doing something was better than nothing",
            genre = "action,drama,comedy",
            rate = "R",
            year = 1997,
            user_score = 89
        )

        val tvShowIntent = Intent(
            ApplicationProvider.getApplicationContext(),
            DetailActivity::class.java
        ).apply {
            putExtra(DETAIL_EXTRA_TV_SHOW, tvShow)
        }
        launchActivity<DetailActivity>(tvShowIntent)
        onView(withId(R.id.detail_tv_title)).check(matches(withText("${tvShow.title}(${tvShow.year})")))
        onView(withId(R.id.detail_tv_desc)).check(matches(withText(tvShow.description)))
        onView(withId(R.id.detail_tv_release_date)).check(matches(withText("")))
        onView(withId(R.id.detail_tv_genre)).check(matches(withText("${tvShow.rate} | ${tvShow.genre}")))
        onView(withId(R.id.detail_tv_score)).check(matches(withText(tvShow.user_score.toString())))
    }

    }