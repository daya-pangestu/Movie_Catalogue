package com.daya.moviecatalogue.ui.main.movie

import android.content.Intent
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.launchFragment
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.daya.moviecatalogue.R
import com.daya.moviecatalogue.data.DataDummy
import com.daya.moviecatalogue.di.idlingresource.IdlingResources
import com.daya.moviecatalogue.ui.detail.DetailActivity
import com.daya.moviecatalogue.ui.util.RecyclerViewItemCountAssertion
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import launchFragmentInHiltContainer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.LooperMode
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
@LooperMode(LooperMode.Mode.PAUSED)
class MovieFragmentInstrumentedTest {

    @get:Rule
    var hiltAndroidRule = HiltAndroidRule(this)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var idlingResources: IdlingResources

    private val dummyMovie = DataDummy.getListMovie()

    @Before
    fun setUp() {
        hiltAndroidRule.inject()
        IdlingRegistry.getInstance().register(idlingResources.idlingResources)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(idlingResources.idlingResources)
    }


    @Test
    fun `recyclerview should load all getListMovie`() {
        launchFragment<MovieFragment>()

        launchFragmentInHiltContainer<>()

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
        launchFragmentInHiltContainer<MovieFragment>{
            onView(withId(R.id.rv_list)).perform(
                    RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                            0
                    ), click()
            )

            val expectedIntent = Intent(context, DetailActivity::class.java)
            val app = ApplicationProvider.getApplicationContext<HiltTestApplication>()
            val actual = shadowOf(app).nextStartedActivity
            assertThat(expectedIntent.component).isEqualTo(actual.component)

        }
    }
}