package mohamad.hoseini.catapi.ui.feature.breeds

import dagger.hilt.android.lifecycle.HiltViewModel
import mohamad.hoseini.catapi.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class BreedsViewModel @Inject constructor() :
    BaseViewModel<BreedsIntent, BreedsState, BreedsEvent>(BreedsState()) {

    override fun handleIntent(intent: BreedsIntent) {
        when (intent) {
            is BreedsIntent.NavigateToBreedsDetailsPage -> TODO()
            BreedsIntent.Refresh -> TODO()
        }
    }
}