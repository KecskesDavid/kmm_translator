package com.example.translator_kmm.featuretranslate.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class TranslateResponse(
    val translatedText: String
)