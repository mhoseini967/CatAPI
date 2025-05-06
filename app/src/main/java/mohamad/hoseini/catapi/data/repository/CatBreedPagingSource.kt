package mohamad.hoseini.catapi.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import mohamad.hoseini.catapi.data.local.database.dao.CatBreedDao
import mohamad.hoseini.catapi.data.mapper.toDomain
import mohamad.hoseini.catapi.domain.model.CatBreed

class CatBreedPagingSource(
    private val catBreedDao: CatBreedDao
) : PagingSource<Int, CatBreed>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CatBreed> {
        val page = params.key ?: 1
        val pageSize = params.loadSize

        return try {
            val catBreeds = catBreedDao.getCatBreedsPaginated(page, pageSize)
            val domainBreeds = catBreeds.map { it.toDomain() }
            LoadResult.Page(
                data = domainBreeds,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (catBreeds.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CatBreed>): Int? {
        return state.anchorPosition?.let { position ->
            val page = state.closestPageToPosition(position)
            page?.prevKey ?: page?.nextKey
        }
    }
}
