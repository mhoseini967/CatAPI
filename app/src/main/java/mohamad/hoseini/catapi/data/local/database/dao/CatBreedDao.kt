package mohamad.hoseini.catapi.data.local.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import mohamad.hoseini.catapi.base.BaseRoomDao
import mohamad.hoseini.catapi.data.local.database.entity.CatBreedEntity

@Dao
interface CatBreedDao : BaseRoomDao<CatBreedEntity> {

    @Query("SELECT * FROM cat_breeds where name LIKE '%' || :searchFilter || '%' ORDER BY id ASC")
    fun getCatBreedsPaginated(searchFilter: String): PagingSource<Int, CatBreedEntity>

    @Query("DELETE FROM cat_breeds")
    suspend fun clearAll()
}