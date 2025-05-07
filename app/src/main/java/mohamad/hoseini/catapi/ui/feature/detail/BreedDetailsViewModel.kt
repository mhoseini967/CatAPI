package mohamad.hoseini.catapi.ui.feature.detail

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import mohamad.hoseini.catapi.base.BaseViewModel
import mohamad.hoseini.catapi.domain.usecase.GetCatBreedDetailsUseCase
import javax.inject.Inject

@HiltViewModel
class BreedDetailsViewModel @Inject constructor(
    private val getCatBreedDetailsUseCase: GetCatBreedDetailsUseCase
) :
    BaseViewModel<BreedDetailsIntent, BreedDetailsState, BreedDetailsEvent>(BreedDetailsState()) {


    override fun handleIntent(intent: BreedDetailsIntent) {
        when (intent) {
            BreedDetailsIntent.ToggleLike -> toggleLike()
            is BreedDetailsIntent.FetchBreedDetails -> fetchBreedDetails(intent.breedId)
        }
    }

    private fun toggleLike() {
        TODO("Not yet implemented")
    }

    private fun fetchBreedDetails(breedId: String) {
        viewModelScope.launch {
            getCatBreedDetailsUseCase(breedId)
                .onStart { updateState { copy(isLoading = true) } }
                .catch { sendEvent(BreedDetailsEvent.ShowMessage("Unable to fetch breed details. Please try again later.")) }
                .collect {
                    updateState { copy(breed = it, isLoading = false) }
                }
        }
    }


}