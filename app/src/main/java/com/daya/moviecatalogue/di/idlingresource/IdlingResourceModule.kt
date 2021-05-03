package com.daya.moviecatalogue.di.idlingresource

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
abstract class IdlingResourceModule {

    @DebugIdlingRes
    @Binds
    abstract fun bindIdlingResourceEspresso(espressoIdlingResources: EspressoIdlingResource): IdlingResources

    @ProductionIdlingRes
    @Binds
    abstract fun bindIdlingResourceProduction(productionIdlingResourceModule: ProductionIdlingResource): IdlingResources

}


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DebugIdlingRes

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ProductionIdlingRes



