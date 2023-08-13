package com.plcoding.translator_kmm.core.domain.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

// By making this open, the CommonStateFlow file inherits the subscribe method
actual open class CommonFlow<T> actual constructor(
    private val flow: Flow<T>
) : Flow<T> by flow {

    fun subscribe(
        coroutineScope: CoroutineScope,
        dispatcher: CoroutineDispatcher,
        onCollect: (T) -> Unit
    ): IDisposableHandle {
        val job = coroutineScope.launch(dispatcher) {
            flow.collect(onCollect)
        }
        return IDisposableHandle { job.cancel() }
    }

    fun subscribe(
        onCollect: (T) -> Unit
    ): IDisposableHandle {
        return subscribe(
            coroutineScope = GlobalScope,
            dispatcher = Dispatchers.Main,
            onCollect = onCollect
        )
    }
}