package com.plcoding.translator_kmm.core.domain.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

actual open class CommonStateFlow<T> actual constructor(
    private val stateFlow: StateFlow<T>
) : CommonFlow<T>(stateFlow), StateFlow<T> {
    override val replayCache: List<T>
        get() = stateFlow.replayCache
    override val value: T
        get() = stateFlow.value

    override suspend fun collect(collector: FlowCollector<T>): Nothing {
        stateFlow.collect(collector)
    }
}