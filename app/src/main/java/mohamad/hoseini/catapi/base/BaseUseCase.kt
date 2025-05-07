package mohamad.hoseini.catapi.base

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import mohamad.hoseini.catapi.di.DispatcherProvider
import mohamad.hoseini.catapi.domain.model.CatBreed

abstract class BaseUseCase<in Params, out Result>(private val dispatcherProvider: DispatcherProvider) {

    // Generic invoke method, works for both Flow and non-Flow results
    suspend operator fun invoke(params: Params = Unit as Params): Result {
        return withContext(dispatcherProvider.io) {
            execute(params).applyDispatcherIfFlow(dispatcherProvider)
        }
    }

    // Abstract execute method, to be implemented by subclasses
    protected abstract suspend fun execute(params: Params): Result
}

private fun <T> T.applyDispatcherIfFlow(dispatcherProvider: DispatcherProvider): T {
    return if (this is Flow<*>) {
        @Suppress("UNCHECKED_CAST")
        (this as Flow<T>).flowOn(dispatcherProvider.io) as T
    } else {
        this
    }
}
