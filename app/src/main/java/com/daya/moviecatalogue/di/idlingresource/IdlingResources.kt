package com.daya.moviecatalogue.di.idlingresource

import androidx.test.espresso.idling.CountingIdlingResource

interface IdlingResources {
    fun increment()
    fun decrement()
}

object ProductionIdlingResource : IdlingResources {
    //do nothing in production
    override fun increment() = Unit
    override fun decrement() = Unit
}

object EspressoIdlingResource : IdlingResources {
    private const val RESOURCE = "GLOBAL"
    val idlingResources = CountingIdlingResource(RESOURCE)
    override fun increment() {
        idlingResources.increment()
    }

    override fun decrement() {
        idlingResources.decrement()
    }

}