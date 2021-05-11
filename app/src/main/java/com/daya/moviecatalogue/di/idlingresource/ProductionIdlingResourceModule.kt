package com.daya.moviecatalogue.di.idlingresource

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ProductionIdlingResourceModule {

    @Binds
    @Singleton
    abstract fun bindIdlingResourceEspresso(prodIdlingResources: ProductionIdlingResource): IdlingResources
}

//
//@Qualifier
//@Retention(AnnotationRetention.BINARY)
//annotation class DebugIdlingRes
//
//@Qualifier
//@Retention(AnnotationRetention.BINARY)
//annotation class ProductionIdlingRes


