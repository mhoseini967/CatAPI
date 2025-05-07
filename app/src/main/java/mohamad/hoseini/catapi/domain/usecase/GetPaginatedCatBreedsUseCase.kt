package mohamad.hoseini.catapi.domain.usecase

import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.withContext
import mohamad.hoseini.catapi.base.BaseUseCase
import mohamad.hoseini.catapi.di.DispatcherProvider
import mohamad.hoseini.catapi.domain.model.CatBreed
import mohamad.hoseini.catapi.domain.repository.CatBreedRepository
import mohamad.hoseini.catapi.domain.repository.FavoriteBreedRepository
import javax.inject.Inject

class GetPaginatedCatBreedsUseCase @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val catBreedRepository: CatBreedRepository,
    private val favoriteBreedRepository: FavoriteBreedRepository
) :
    BaseUseCase<String, Flow<PagingData<CatBreed>>>(dispatcherProvider) {
    override suspend fun execute(params: String): Flow<PagingData<CatBreed>> {
        return catBreedRepository.getCatBreedsPaged(params)
            .map { pagingData ->
                pagingData.map { breed ->
                    withContext(dispatcherProvider.io){
                        // For each breed, check if it's a favorite and update accordingly
                        val isFavorite = favoriteBreedRepository.isBreedFavorite(breed.breedId)
                        breed.copy(isFavorite = isFavorite)
                    }
                }
            }
    }

}