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

object DebugIdlingResource {
    private const val RESOURCE = "GLOBAL"

     var idlingResources: CountingIdlingResource = CountingIdlingResource(RESOURCE)

     fun increment() {
        idlingResources.increment()
    }

     fun decrement() {
         idlingResources.decrement()
    }


}