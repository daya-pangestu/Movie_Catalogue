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

object TestIdlingResource {
        private const val RESOURCE = "GLOBAL"

    val get: CountingIdlingResource = CountingIdlingResource(RESOURCE)

     fun increment() {
        get.increment()
    }

    fun decrement() {
        get.decrement()
    }
}