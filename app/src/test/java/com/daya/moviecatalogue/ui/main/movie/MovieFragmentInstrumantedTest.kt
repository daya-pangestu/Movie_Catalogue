package com.daya.moviecatalogue.ui.main.movie

import androidx.fragment.app.testing.launchFragment
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.daya.moviecatalogue.R
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MovieFragmentInstrumantedTest{

    @Test
    fun testMovieFragment() {
        val scenario = launchFragment<MovieFragment>()
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))
    }

}