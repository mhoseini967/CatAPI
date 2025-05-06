package mohamad.hoseini.catapi.data.local.database.dao

import androidx.paging.PagingSource
import androidx.room.Query
import mohamad.hoseini.catapi.base.BaseRoomDao
import mohamad.hoseini.catapi.data.local.database.entity.CatBreedEntity

interface CatBreedDao : BaseRoomDao<CatBreedEntity> {

    @Query("SELECT * FROM cat_breeds LIMIT :pageSize OFFSET :pageSize")
    suspend fun getCatBreedsPaginated(page: Int, pageSize: Int): List<CatBreedEntity>

    @Query("DELETE FROM cat_breeds")
    suspend fun clearAll()
}