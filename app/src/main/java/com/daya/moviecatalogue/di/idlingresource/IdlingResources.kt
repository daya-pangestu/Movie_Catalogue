package com.daya.moviecatalogue.di.idlingresource

import androidx.test.espresso.IdlingResource
import androidx.test.espresso.idling.CountingIdlingResource
import java.lang.Exception

object TestIdlingResource {
    private const val RESOURCE = "GLOBAL"

    @JvmField val get: CountingIdlingResource = CountingIdlingResource(RESOURCE)

     fun increment() {
        get.increment()
    }

    fun decrement() {
        if (!get.isIdleNow) {
            get.decrement()
        }
    }
}