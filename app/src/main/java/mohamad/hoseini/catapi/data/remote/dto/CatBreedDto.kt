package mohamad.hoseini.catapi.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CatBreedDto(
    @SerializedName("id")
    val breedId: String,
    val name: String,
    val description: String,
    val temperament: String,
    val origin: String,
    val weight: Weight,
    @SerializedName("country_code")
    val countryCode: String,
    @SerializedName("life_span")
    val lifeSpan: String,
    @SerializedName("adaptability")
    val adaptability: Int,
    @SerializedName("affection_level")
    val affectionLevel: Int,
    @SerializedName("child_friendly")
    val childFriendly: Int,
    @SerializedName("wikipedia_url")
    val wikipediaUrl: String?,
    @SerializedName("reference_image_id")
    val imageId: String?
)

data class Weight(
    val imperial: String,
    val metric: String
)
