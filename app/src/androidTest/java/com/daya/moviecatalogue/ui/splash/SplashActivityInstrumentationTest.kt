package com.daya.moviecatalogue.ui.splash

import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SplashActivityInstrumentationTest{

    @get:Rule
    var activityScenarioRule = activityScenarioRule<SplashActivity>()

    @Test
    fun splash() {
        val scenario = activityScenarioRule.scenario
        scenario.onActivity {
            assertThat(it).isNotNull()
        }
    }
}