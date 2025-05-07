package mohamad.hoseini.catapi.domain.usecase

import kotlinx.coroutines.flow.Flow
import mohamad.hoseini.catapi.base.BaseUseCase
import mohamad.hoseini.catapi.di.DispatcherProvider
import mohamad.hoseini.catapi.domain.model.CatBreed
import mohamad.hoseini.catapi.domain.repository.CatBreedRepository
import javax.inject.Inject

class GetCatBreedDetailsUseCase @Inject constructor(
    dispatcherProvider: DispatcherProvider,
    private val catBreedRepository: CatBreedRepository
) :
    BaseUseCase<String, Flow<CatBreed>>(dispatcherProvider) {
    override suspend fun execute(params: String): Flow<CatBreed> {
        return catBreedRepository.getCatBreedById(breedId = params)
    }

}