package mohamad.hoseini.catapi.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mohamad.hoseini.catapi.data.repository.FavoriteBreedRepositoryImpl
import mohamad.hoseini.catapi.data.repository.breed.CatBreedRepositoryImpl
import mohamad.hoseini.catapi.domain.repository.CatBreedRepository
import mohamad.hoseini.catapi.domain.repository.FavoriteBreedRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCatBreedRepository(catBreedRepositoryImpl: CatBreedRepositoryImpl): CatBreedRepository

    @Binds
    @Singleton
    abstract fun bindFavoriteBreedRepository(catBreedRepositoryImpl: FavoriteBreedRepositoryImpl): FavoriteBreedRepository


}
