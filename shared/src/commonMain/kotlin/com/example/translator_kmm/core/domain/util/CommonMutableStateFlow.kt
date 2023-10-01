package com.example.translator_kmm.core.domain.util

import kotlinx.coroutines.flow.MutableStateFlow

expect open class CommonMutableStateFlow<T>(mutableStateFlow: MutableStateFlow<T>): MutableStateFlow<T>

fun <T> MutableStateFlow<T>.toCommonMutableStateFlow() = CommonMutableStateFlow(this)