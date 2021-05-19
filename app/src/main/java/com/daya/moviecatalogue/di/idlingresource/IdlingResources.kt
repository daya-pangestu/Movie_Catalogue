package com.daya.moviecatalogue.di.idlingresource

import androidx.test.espresso.idling.CountingIdlingResource

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