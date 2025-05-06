package mohamad.hoseini.catapi.ui.feature.breeds

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems

@Composable
fun CatBreedListRoute(
    viewModel: BreedsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    CatBreedListScreen(
        state = state,
        onIntentCallback = { viewModel.handleIntent(it) }
    )
}


@Composable
fun CatBreedListScreen(state: BreedsState, onIntentCallback: (intent: BreedsIntent) -> Unit) {
    val breeds = state.breedsPagingFlow.collectAsLazyPagingItems()
    Text("${breeds.itemCount}")
}