package com.daya.moviecatalogue.ui.main.movie

import androidx.fragment.app.testing.launchFragment
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.daya.moviecatalogue.R
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MovieFragmentTest{

    @Test
    fun testMovieFragment() {
        val scenario = launchFragment<MovieFragment>()
        Espresso.onView(ViewMatchers.withId(R.id.rv_movie))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

}