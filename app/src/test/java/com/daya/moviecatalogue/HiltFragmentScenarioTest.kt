package com.daya.moviecatalogue

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.daya.moviecatalogue.ui.main.movie.MovieFragment
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import launchFragmentInHiltContainer
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class HiltFragmentScenarioTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun testLaunchFragment() {
        launchFragmentInHiltContainer<MovieFragment> {  }
    }

}