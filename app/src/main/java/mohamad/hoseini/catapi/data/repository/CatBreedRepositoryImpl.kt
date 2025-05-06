package mohamad.hoseini.catapi.data.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import mohamad.hoseini.catapi.data.remote.api.CatBreedApi
import mohamad.hoseini.catapi.domain.model.CatBreed
import mohamad.hoseini.catapi.domain.repository.CatBreedRepository
import javax.inject.Inject

class CatBreedRepositoryImpl @Inject constructor(
    private val catBreedApi: CatBreedApi
) : CatBreedRepository {

    override fun getCatBreedsPaged(): Flow<PagingData<CatBreed>> {
        TODO("Not yet implemented")
    }

    override suspend fun refreshCatBreeds(): Result<Unit> {
        TODO("Not yet implemented")
    }
}