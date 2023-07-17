package com.plcoding.translator_kmm.featuretranslate.data.remote

import io.ktor.client.HttpClient

expect class HttpClientFactory {
    fun create(): HttpClient
}