package com.daya.moviecatalogue.di.idlingresource

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
abstract class IdlingResourceModule {
    @Binds
    @EspressoIdlingRes
    abstract fun bindIdlingResourceEspresso(espressoIdlingResources: EspressoIdlingResource): IdlingResources

    @Binds
    @ProductionIdlingRes
    abstract fun bindIdlingResourceProduction(productionIdlingResourceModule: ProductionIdlingResource): IdlingResources

}


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class EspressoIdlingRes

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ProductionIdlingRes



