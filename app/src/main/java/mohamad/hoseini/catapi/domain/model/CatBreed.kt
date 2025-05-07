package mohamad.hoseini.catapi.domain.model

data class CatBreed(
    val id: Int,
    val breedId: String,
    val name: String,
    val description : String,
    val temperaments: List<String>,
    val origin : String,
    val countryCode : String,
    val lifeSpan: String,
    val weight: String,
    val adaptability: Int,
    val affectionLevel : Int,
    val childFriendly: Int,
    val wikipediaUrl : String?,
    val imageUrl: String?,
    val isFavorite: Boolean = false
)

