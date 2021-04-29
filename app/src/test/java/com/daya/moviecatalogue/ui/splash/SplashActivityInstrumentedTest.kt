package com.daya.moviecatalogue.ui.splash

import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.daya.moviecatalogue.ui.main.MainActivity
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltTestApplication
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Shadows.shadowOf
import org.robolectric.shadows.ShadowLooper

@RunWith(AndroidJUnit4::class)
class SplashActivityInstrumentedTest {
    @get:Rule
    val activityRule = activityScenarioRule<SplashActivity>()

    @Test
    fun `supportActionBar  should be hidden`() {
        val scenario = activityRule.scenario
        scenario.onActivity {
            val toolbar = it.supportActionBar
            assertThat(toolbar).isNotNull()
            assertThat(toolbar).isNotEqualTo(!toolbar!!.isShowing)
        }
    }

    @Test
    fun `splashActivity Should Intent Automatically to MainActivity`() {
        val scenario = activityRule.scenario
        scenario.onActivity {
            it.intentIntoMainActivity()
            ShadowLooper.runUiThreadTasksIncludingDelayedTasks()
            val expectedIntent = Intent(it, MainActivity::class.java)
            val app = ApplicationProvider.getApplicationContext<HiltTestApplication>()
            val actual = shadowOf(app).nextStartedActivity
            assertThat(expectedIntent.component).isEqualTo(actual.component)
        }
    }
}