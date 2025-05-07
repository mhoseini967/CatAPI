package mohamad.hoseini.catapi.data.repository

import mohamad.hoseini.catapi.data.local.database.dao.FavoriteBreedDao
import mohamad.hoseini.catapi.data.local.database.entity.FavoriteBreedEntity
import mohamad.hoseini.catapi.domain.repository.FavoriteBreedRepository
import javax.inject.Inject

class FavoriteBreedRepositoryImpl @Inject constructor(
    private val favoriteBreedDao: FavoriteBreedDao
) : FavoriteBreedRepository {

    override suspend fun isBreedFavorite(breedId: String): Boolean =
        favoriteBreedDao.isBreedFavorite(breedId)

    override suspend fun unFavoriteBreed(breedId: String) {
        favoriteBreedDao.unFavoriteBreed(breedId)
    }

    override suspend fun addFavoriteBreed(breedId: String) {
        favoriteBreedDao.insert(FavoriteBreedEntity(id = 0, breedId))
    }
}