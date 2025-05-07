package mohamad.hoseini.catapi.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import mohamad.hoseini.catapi.domain.model.CatBreed

interface CatBreedRepository {
    fun getCatBreedsPaged(searchFilter: String = "") : Flow<PagingData<CatBreed>>
    suspend fun refreshCatBreeds() : Result<Unit>
}