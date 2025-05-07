package mohamad.hoseini.catapi.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import mohamad.hoseini.catapi.base.BaseUseCase
import mohamad.hoseini.catapi.di.DispatcherProvider
import mohamad.hoseini.catapi.domain.model.CatBreed
import mohamad.hoseini.catapi.domain.repository.CatBreedRepository
import mohamad.hoseini.catapi.domain.repository.FavoriteBreedRepository
import javax.inject.Inject


class GetCatBreedDetailsUseCase @Inject constructor(
    dispatcherProvider: DispatcherProvider,
    private val catBreedRepository: CatBreedRepository,
    private val favoriteBreedRepository: FavoriteBreedRepository
) : BaseUseCase<String, Flow<CatBreed>>(dispatcherProvider) {
    override suspend fun execute(params: String): Flow<CatBreed> {
        return catBreedRepository.getCatBreedById(breedId = params)
            .map { breed ->
                val isFavorite = favoriteBreedRepository.isBreedFavorite(breed.breedId)
                breed.copy(isFavorite = isFavorite)
            }
    }

}