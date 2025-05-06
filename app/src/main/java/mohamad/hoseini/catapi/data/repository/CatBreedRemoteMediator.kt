package mohamad.hoseini.catapi.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import mohamad.hoseini.catapi.data.local.database.dao.CatBreedDao
import mohamad.hoseini.catapi.data.local.database.entity.CatBreedEntity
import mohamad.hoseini.catapi.data.mapper.toEntity
import mohamad.hoseini.catapi.data.remote.api.CatBreedApi
import mohamad.hoseini.catapi.domain.model.CatBreed
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class CatBreedRemoteMediator(
    private val catBreedApi: CatBreedApi,
    private val catBreedDao: CatBreedDao
) : RemoteMediator<Int, CatBreed>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CatBreed>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> 1 // Always fetch from the start for refresh
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    // Check the last item from the PagingState and determine the page number
                    val lastItem =
                        state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
                    val lastItemId = lastItem?.id?.toIntOrNull() ?: 1
                    lastItemId + 1 // Increment page number for append
                }
            }

            val response = catBreedApi.getBreeds(page)
            val breeds = if(response.isSuccessful) response.body() else emptyList()

            // Store fetched breeds in the Room database
            if (loadType == LoadType.REFRESH) {
                // Clear the database for fresh data when refreshing
                catBreedDao.clearAll()
            }
            breeds?.let { dto -> catBreedDao.insertAll(dto.map { it.toEntity() }) }

            // Return success with information about whether we've reached the end of the data
            MediatorResult.Success(endOfPaginationReached = breeds.isNullOrEmpty())

        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}
