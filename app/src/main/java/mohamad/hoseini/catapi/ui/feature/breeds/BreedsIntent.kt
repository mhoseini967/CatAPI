package mohamad.hoseini.catapi.ui.feature.breeds

sealed class BreedsIntent {
    data object FetchData : BreedsIntent()
    data object Refresh : BreedsIntent()
    data class SetSearchingMode(val searchingMode: Boolean) : BreedsIntent()
    data object ToggleTheme : BreedsIntent()
    data class SearchBreed(val text: String) : BreedsIntent()
}