package mohamad.hoseini.catapi.data.repository.breed

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import mohamad.hoseini.catapi.constants.AppConstants
import mohamad.hoseini.catapi.data.local.database.dao.CatBreedDao
import mohamad.hoseini.catapi.data.local.database.entity.CatBreedEntity
import mohamad.hoseini.catapi.data.mapper.toEntity
import mohamad.hoseini.catapi.data.remote.api.CatBreedApi
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class CatBreedRemoteMediator(
    private val catBreedApi: CatBreedApi,
    private val catBreedDao: CatBreedDao,
    private val searchFilter: String = ""
) : RemoteMediator<Int, CatBreedEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CatBreedEntity>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> 0
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val currentSize = state.pages.sumOf { it.data.size }
                    (currentSize / AppConstants.CAT_BREEDS_PAGE_SIZE) + 1
                }
            }
            Timber.d("Load breeds page: $page")

            val response = if (searchFilter.isEmpty()) {
                catBreedApi.getBreeds(page, AppConstants.CAT_BREEDS_PAGE_SIZE)
            } else {
                catBreedApi.searchBreeds(
                    page,
                    AppConstants.CAT_BREEDS_PAGE_SIZE,
                    searchFilter = searchFilter
                )
            }
            val breeds = if (response.isSuccessful) response.body() else emptyList()

            if (!breeds.isNullOrEmpty()) {
                if (loadType == LoadType.REFRESH) {
                    catBreedDao.clearAll()
                }
                catBreedDao.insertAll(breeds.map { it.toEntity() })
            }

            MediatorResult.Success(endOfPaginationReached = breeds.isNullOrEmpty())

        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}
