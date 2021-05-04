package com.daya.moviecatalogue.di.idlingresource

import androidx.test.espresso.idling.CountingIdlingResource
import javax.inject.Inject

interface IdlingResources {
    var idlingResources : CountingIdlingResource?
    fun increment()
    fun decrement()
}

class ProductionIdlingResource @Inject constructor() : IdlingResources {
    //do nothing in production
    override var idlingResources : CountingIdlingResource? = null
    override fun increment() = Unit
    override fun decrement() = Unit
}

class DebugIdlingResource @Inject constructor() : IdlingResources {

    override var idlingResources: CountingIdlingResource? = CountingIdlingResource(RESOURCE)
    override fun increment() {
        idlingResources?.increment()
    }

    override fun decrement() {
        val isIdle : Boolean = idlingResources?.isIdleNow ?: false
        if (isIdle) idlingResources?.decrement()
    }

    companion object {
        private const val RESOURCE = "GLOBAL"
    }
}