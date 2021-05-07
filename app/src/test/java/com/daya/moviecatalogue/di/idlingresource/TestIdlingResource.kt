package com.daya.moviecatalogue.di.idlingresource

import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [ProductionIdlingResources::class]
)
abstract class TestIdlingResource{

    @Binds
    @Singleton
    abstract fun bindIdlingResourceProduction(productionIdlingResourceModule: ProductionIdlingResource): IdlingResources

}
