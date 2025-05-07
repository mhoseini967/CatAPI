package mohamad.hoseini.catapi.ui.feature.breeds

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import mohamad.hoseini.catapi.domain.model.CatBreed

data class BreedsState(
    val breedsPagingFlow: Flow<PagingData<CatBreed>> = emptyFlow(),
    val isRefreshing: Boolean = false,
    val searchLoading: Boolean = false,
    val searchMode: Boolean = false,
    val searchingText: String= "",
)