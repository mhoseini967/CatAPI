package mohamad.hoseini.catapi.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

abstract class BaseViewModel<I, S, E>(initialState: S) : ViewModel() {

    private var _state = MutableStateFlow(initialState)
    val state: StateFlow<S> = _state.asStateFlow()

    private val _event = MutableSharedFlow<E?>()
    val event: SharedFlow<E?> = _event.asSharedFlow()

    fun setState(newState: S) {
        _state.value = newState
    }

    protected fun updateState(reducer: S.() -> S) {
        _state.update(reducer)
    }

    protected suspend fun sendEvent(newEvent: E?) {
        _event.emit(newEvent)
    }

    abstract fun handleIntent(intent: I)
}

