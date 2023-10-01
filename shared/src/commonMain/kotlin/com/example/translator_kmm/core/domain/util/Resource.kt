package com.example.translator_kmm.core.domain.util

sealed class Resource<T>(val data: T?, val error: Throwable? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(error: Throwable): Resource<T>(null, error)
}
