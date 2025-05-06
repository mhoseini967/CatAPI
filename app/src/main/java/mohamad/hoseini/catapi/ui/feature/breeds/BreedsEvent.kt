package mohamad.hoseini.catapi.ui.feature.breeds

sealed class BreedsEvent {
    data class ShowMessage(val message: String) : BreedsEvent()
}