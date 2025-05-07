package mohamad.hoseini.catapi.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import mohamad.hoseini.catapi.base.BaseUseCase
import mohamad.hoseini.catapi.di.DispatcherProvider
import mohamad.hoseini.catapi.domain.repository.CatBreedRepository
import javax.inject.Inject

class RefreshCatBreedsUseCase @Inject constructor(
    dispatcherProvider: DispatcherProvider,
    private val catBreedRepository: CatBreedRepository
) :
    BaseUseCase<Unit, Flow<Result<Unit>>>(dispatcherProvider) {
    override suspend fun execute(params: Unit): Flow<Result<Unit>> = flow {
        emit(catBreedRepository.refreshCatBreeds())
    }

}