package com.daya.moviecatalogue.di.idlingresource

import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import com.google.common.truth.Truth.assertThat
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class IdlingResourceTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    @ProductionIdlingRes
    lateinit var productionIdlingRes: IdlingResources

    @Inject
    @DebugIdlingRes
    lateinit var debugIdlingRes: IdlingResources

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun `debugIdlingresources should not be null in debug`() {
        assertThat(debugIdlingRes).isNotNull()
        assertThat(debugIdlingRes.idlingResources).isNotNull()
    }

    @Test
    fun `productionIdlingresources should be null in production`() {
        assertThat(productionIdlingRes).isNotNull()
        assertThat(productionIdlingRes.idlingResources).isNull()
    }

}