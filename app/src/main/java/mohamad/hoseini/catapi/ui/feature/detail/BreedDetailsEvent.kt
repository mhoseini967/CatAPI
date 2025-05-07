package mohamad.hoseini.catapi.ui.feature.detail

sealed class BreedDetailsEvent {
    data class ShowMessage(val message: String) : BreedDetailsEvent()
}