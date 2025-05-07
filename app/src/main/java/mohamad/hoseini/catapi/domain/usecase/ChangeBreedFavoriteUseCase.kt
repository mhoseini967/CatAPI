package mohamad.hoseini.catapi.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import mohamad.hoseini.catapi.base.BaseUseCase
import mohamad.hoseini.catapi.di.DispatcherProvider
import mohamad.hoseini.catapi.domain.repository.FavoriteBreedRepository
import javax.inject.Inject

data class ChangeBreedFavoriteParams(
    val breedId: String,
    val isFavorite: Boolean
)

class ChangeBreedFavoriteUseCase @Inject constructor(
    dispatcherProvider: DispatcherProvider,
    private val favoriteBreedRepository: FavoriteBreedRepository
) :
    BaseUseCase<ChangeBreedFavoriteParams, Flow<Unit>>(dispatcherProvider) {
    override suspend fun execute(params: ChangeBreedFavoriteParams): Flow<Unit> = flow {
        emit(
            if (params.isFavorite) {
                favoriteBreedRepository.addFavoriteBreed(breedId = params.breedId)
            } else {
                favoriteBreedRepository.unFavoriteBreed(
                    breedId = params.breedId)
            }
        )
    }

}