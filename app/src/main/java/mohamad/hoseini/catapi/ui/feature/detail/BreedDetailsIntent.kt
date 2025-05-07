package mohamad.hoseini.catapi.ui.feature.detail

sealed class BreedDetailsIntent {
    data object ToggleLike : BreedDetailsIntent()
    data class FetchBreedDetails(val breedId: String) : BreedDetailsIntent()
}