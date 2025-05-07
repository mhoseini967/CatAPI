package mohamad.hoseini.catapi.domain.repository

interface FavoriteBreedRepository {
    suspend fun isBreedFavorite(breedId: String): Boolean
    suspend fun unFavoriteBreed(breedId: String)
    suspend fun addFavoriteBreed(breedId: String)
}