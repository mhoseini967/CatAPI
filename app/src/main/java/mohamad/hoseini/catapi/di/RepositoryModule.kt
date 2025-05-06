package mohamad.hoseini.catapi.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mohamad.hoseini.catapi.data.repository.CatBreedRepositoryImpl
import mohamad.hoseini.catapi.domain.repository.CatBreedRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindRouterRepository(routerRepositoryImpl: CatBreedRepositoryImpl): CatBreedRepository

}
