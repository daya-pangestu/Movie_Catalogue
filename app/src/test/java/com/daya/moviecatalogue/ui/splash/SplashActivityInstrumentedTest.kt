package com.daya.moviecatalogue.ui.splash

import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.daya.moviecatalogue.ui.MovieCatApplication
import com.daya.moviecatalogue.ui.main.MainActivity
import com.google.common.truth.Truth.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Shadows.shadowOf
import org.robolectric.shadows.ShadowLooper

@RunWith(AndroidJUnit4::class)
class SplashActivityInstrumentedTest {
    @get:Rule
    val activityRule = ActivityTestRule(SplashActivity::class.java)

    @Test
    fun `toolbar should be hidden`(){
        val activity = activityRule.activity
        val toolbar = activity.supportActionBar!!
        assertThat(toolbar).isNotNull()
        assertThat(toolbar).isNotEqualTo(toolbar.isShowing)
    }

    @Test
    fun `splashActivity Should Intent Automatically to MainActivity`() {
        val activity = activityRule.activity
        activity.intentIntoMainActivity()
        ShadowLooper.runUiThreadTasksIncludingDelayedTasks()
        val expectedIntent = Intent(activity, MainActivity::class.java)
        val app = ApplicationProvider.getApplicationContext<MovieCatApplication>()
        val actual = shadowOf(app).nextStartedActivity
        assertThat(expectedIntent.component).isEqualTo(actual.component)
    }
}