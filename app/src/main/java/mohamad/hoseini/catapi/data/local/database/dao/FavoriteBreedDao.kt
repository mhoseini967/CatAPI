package mohamad.hoseini.catapi.data.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import mohamad.hoseini.catapi.base.BaseRoomDao
import mohamad.hoseini.catapi.data.local.database.entity.CatBreedEntity
import mohamad.hoseini.catapi.data.local.database.entity.FavoriteBreedEntity

@Dao
interface FavoriteBreedDao : BaseRoomDao<FavoriteBreedEntity> {

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_breeds WHERE breed_id = :breedId LIMIT 1)")
    fun isBreedFavorite(breedId: String): Boolean

    @Query("DELETE FROM favorite_breeds where breed_id = :breedId")
    suspend fun unFavoriteBreed(breedId: String)

}