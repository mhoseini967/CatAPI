package mohamad.hoseini.catapi.data.remote.api

import mohamad.hoseini.catapi.data.remote.dto.CatBreedDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CatBreedApi {
    @GET("v1/breeds")
    suspend fun getBreeds(
        @Query("page") page: Int = 0,
        @Query("limit") limit: Int = 10
    ): Response<List<CatBreedDto>>

    @GET("v1/breeds/search")
    suspend fun searchBreeds(
        @Query("page") page: Int = 0,
        @Query("limit") limit: Int = 10,
        @Query("q") searchFilter: String
    ): Response<List<CatBreedDto>>
}