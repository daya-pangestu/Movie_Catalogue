package com.daya.moviecatalogue.di.idlingresource

import androidx.test.espresso.idling.CountingIdlingResource
import javax.inject.Inject
import javax.inject.Singleton

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

    val idlingResources: CountingIdlingResource = CountingIdlingResource(RESOURCE)

     fun increment() {
        idlingResources.increment()
    }

    fun decrement() {
        idlingResources.decrement()
    }
}