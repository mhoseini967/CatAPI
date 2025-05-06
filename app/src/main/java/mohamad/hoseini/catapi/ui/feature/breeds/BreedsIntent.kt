package mohamad.hoseini.catapi.ui.feature.breeds

sealed class BreedsIntent {
    data object Refresh : BreedsIntent()
}