package com.plcoding.translator_kmm.translate.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class TranslateResponse(
    val translatedText: String
)