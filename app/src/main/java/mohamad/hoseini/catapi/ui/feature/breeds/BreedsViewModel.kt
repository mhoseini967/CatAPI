package mohamad.hoseini.catapi.ui.feature.breeds

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import mohamad.hoseini.catapi.base.BaseViewModel
import mohamad.hoseini.catapi.domain.usecase.GetPaginatedCatBreedsUseCase
import mohamad.hoseini.catapi.domain.usecase.RefreshCatBreedsUseCase
import javax.inject.Inject

@HiltViewModel
class BreedsViewModel @Inject constructor(
    private val refreshCatBreedsUseCase: RefreshCatBreedsUseCase,
    private val getPaginatedCatBreedsUseCase: GetPaginatedCatBreedsUseCase
) :
    BaseViewModel<BreedsIntent, BreedsState, BreedsEvent>(BreedsState()) {

    init {
        observePaginatedBreeds()
        refreshBreeds()
    }

    override fun handleIntent(intent: BreedsIntent) {
        when (intent) {
            BreedsIntent.Refresh -> refreshBreeds()
        }
    }

    private fun observePaginatedBreeds() {
        viewModelScope.launch {
            val flow = getPaginatedCatBreedsUseCase()
            updateState { copy(breedsPagingFlow = flow) }
        }
    }

    private fun refreshBreeds() {
        viewModelScope.launch {
            refreshCatBreedsUseCase()
                .onStart { updateState { copy(isRefreshing = true) } }
                .collect {
                    it.fold(
                        onSuccess = {},
                        onFailure = { exception: Throwable ->
                            sendEvent(
                                BreedsEvent.ShowMessage(
                                    exception.message.toString()
                                )
                            )
                        }
                    )
                    updateState { copy(isRefreshing = false) }
                }
        }
    }
}