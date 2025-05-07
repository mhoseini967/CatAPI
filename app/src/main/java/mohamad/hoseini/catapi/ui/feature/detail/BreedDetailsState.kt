package mohamad.hoseini.catapi.ui.feature.detail

import mohamad.hoseini.catapi.domain.model.CatBreed

data class BreedDetailsState(
    val breed: CatBreed? = null,
    val isLoading : Boolean = true
)
