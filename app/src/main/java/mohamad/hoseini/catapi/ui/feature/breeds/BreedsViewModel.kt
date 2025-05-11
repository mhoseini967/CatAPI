package mohamad.hoseini.catapi.ui.feature.breeds

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import mohamad.hoseini.catapi.base.BaseViewModel
import mohamad.hoseini.catapi.data.local.preferences.SharedPreferencesDataSource
import mohamad.hoseini.catapi.domain.usecase.GetPaginatedCatBreedsUseCase
import mohamad.hoseini.catapi.domain.usecase.RefreshCatBreedsUseCase
import javax.inject.Inject

@HiltViewModel
class BreedsViewModel @Inject constructor(
    private val refreshCatBreedsUseCase: RefreshCatBreedsUseCase,
    private val getPaginatedCatBreedsUseCase: GetPaginatedCatBreedsUseCase,
    private val sharedPreferencesDataSource: SharedPreferencesDataSource
) :
    BaseViewModel<BreedsIntent, BreedsState, BreedsEvent>(BreedsState()) {
    private val searchQuery = MutableStateFlow("")


    override fun handleIntent(intent: BreedsIntent) {
        when (intent) {
            BreedsIntent.Refresh -> refreshBreeds()
            is BreedsIntent.SetSearchingMode -> {
                updateState { copy(searchMode = intent.searchingMode) }
                if (!intent.searchingMode) {
                    searchBreed("")
                }
            }

            is BreedsIntent.SearchBreed -> searchBreed(intent.text)
            BreedsIntent.ToggleTheme -> toggleTheme()
            BreedsIntent.FetchData -> fetchData()
        }
    }

    private fun fetchData() {
        observePaginatedCatBreedsWithQuery()
        refreshBreeds()
        observeThemeSettings()
    }

    private fun observeThemeSettings() {
        viewModelScope.launch {
            sharedPreferencesDataSource.settingChangesFlow
                .collect {
                    updateState { copy(isDarkMode = it.isDarkMode) }
                }
        }
    }

    private fun toggleTheme() {
        viewModelScope.launch {
            val currentSetting = sharedPreferencesDataSource.getSettings()
            val newTheme = !currentSetting.isDarkMode
            sharedPreferencesDataSource.saveSettings(currentSetting.copy(isDarkMode = newTheme))
            updateState { copy(isDarkMode = newTheme) }
        }
    }

    private fun searchBreed(text: String) {
        updateState { copy(searchingText = text) }
        searchQuery.value = text
    }

    private fun observePaginatedCatBreedsWithQuery() {
        viewModelScope.launch {
            searchQuery
                .debounce(300)
                .distinctUntilChanged()
                .onEach { updateState { copy(searchLoading = true) } }
                .collectLatest { query ->
                    val flow = getPaginatedCatBreedsUseCase(query)
                    updateState {
                        copy(
                            breedsPagingFlow = flow,
                            searchLoading = false
                        )
                    }
                }
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