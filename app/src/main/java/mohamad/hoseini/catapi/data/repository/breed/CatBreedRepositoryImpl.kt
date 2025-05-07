package mohamad.hoseini.catapi.data.repository.breed

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.filter
import androidx.paging.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import mohamad.hoseini.catapi.constants.AppConstants
import mohamad.hoseini.catapi.data.local.database.dao.CatBreedDao
import mohamad.hoseini.catapi.data.mapper.toDomain
import mohamad.hoseini.catapi.data.mapper.toEntity
import mohamad.hoseini.catapi.data.remote.api.CatBreedApi
import mohamad.hoseini.catapi.domain.model.CatBreed
import mohamad.hoseini.catapi.domain.repository.CatBreedRepository
import mohamad.hoseini.catapi.utils.NetworkUtils
import javax.inject.Inject

class CatBreedRepositoryImpl @Inject constructor(
    private val catBreedApi: CatBreedApi,
    private val catBreedDao: CatBreedDao
) : CatBreedRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getCatBreedsPaged(searchFilter: String): Flow<PagingData<CatBreed>> {
        return Pager(
            config = PagingConfig(
                pageSize = AppConstants.CAT_BREEDS_PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = 10,
            ),
            remoteMediator = CatBreedRemoteMediator(catBreedApi, catBreedDao, searchFilter),
            pagingSourceFactory = {
                catBreedDao.getCatBreedsPaginated(searchFilter)
            }
        ).flow
            .map { pagingData ->
                pagingData
                    .map { it.toDomain() }
                    .distinctById()
            }
    }

    override fun getCatBreedById(breedId: String): Flow<CatBreed> {
        return catBreedDao.getCatBreedById(breedId).map { it.toDomain() }
    }

    override suspend fun refreshCatBreeds(): Result<Unit> {
        return try {
            val response = catBreedApi.getBreeds()
            if (response.isSuccessful) {
                val breeds = response.body() ?: emptyList()
                catBreedDao.insertAll(breeds.map { it.toEntity() })
                Result.success(Unit)
            } else {
                Result.failure(
                    Exception(
                        NetworkUtils.getErrorMessageFromHttpStatusCode(
                            response.code()
                        )
                    )
                )
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun PagingData<CatBreed>.distinctById(): PagingData<CatBreed> {
        val seenIds = mutableSetOf<String>()
        return this.filter { cat ->
            seenIds.add(cat.breedId)
        }
    }
}