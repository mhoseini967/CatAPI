package mohamad.hoseini.catapi.data.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import mohamad.hoseini.catapi.domain.model.CatBreed
import mohamad.hoseini.catapi.domain.repository.CatBreedRepository

class CatBreedRepositoryImpl: CatBreedRepository {

    override fun getCatBreedsPaged(): Flow<PagingData<CatBreed>> {
        TODO("Not yet implemented")
    }

    override suspend fun refreshCatBreeds(): Result<Unit> {
        TODO("Not yet implemented")
    }
}