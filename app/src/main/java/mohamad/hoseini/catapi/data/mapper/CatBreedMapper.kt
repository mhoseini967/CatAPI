package mohamad.hoseini.catapi.data.mapper

import mohamad.hoseini.catapi.constants.AppConstants
import mohamad.hoseini.catapi.data.local.database.entity.CatBreedEntity
import mohamad.hoseini.catapi.data.remote.dto.CatBreedDto
import mohamad.hoseini.catapi.domain.model.CatBreed
import java.util.Date


fun CatBreedEntity.toDomain(): CatBreed {
    return CatBreed(
        id = id,
        breedId = breedId,
        name = name,
        description = description,
        temperaments = temperament.split(",").map { it.trim() },
        origin = origin,
        countryCode = countryCode,
        lifeSpan = lifeSpan,
        weight = weight,
        adaptability = adaptability,
        affectionLevel = affectionLevel,
        childFriendly = childFriendly,
        wikipediaUrl = wikipediaUrl,
        imageUrl = imageId?.let { "${AppConstants.IMAGE_BASE_URL}${imageId}.jpg" },
    )
}

fun CatBreedDto.toEntity(): CatBreedEntity {
    return CatBreedEntity(
        id = 0,
        breedId = breedId,
        name = name,
        description = description,
        temperament = temperament,
        origin = origin,
        countryCode = countryCode,
        lifeSpan = lifeSpan,
        weight = weight.metric,
        adaptability = adaptability,
        affectionLevel = affectionLevel,
        childFriendly = childFriendly,
        wikipediaUrl = wikipediaUrl,
        imageId = imageId,
        createdAt = Date()
    )
}