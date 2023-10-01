package com.example.translator_kmm.featuretranslate.data.entity

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class TranslateRequest(
    @SerialName("q") val textToTranslate: String,
    @SerialName("source") val sourceLanguageCode: String,
    @SerialName("target") val targetLanguageCode: String
)