package com.example.translator_kmm.featuretranslate.domain.translate

import com.example.translator_kmm.core.domain.model.Language

interface ITranslateClient {
    suspend fun translate(
        fromLanguage: Language,
        toLanguage: Language,
        text: String
    ): String
}