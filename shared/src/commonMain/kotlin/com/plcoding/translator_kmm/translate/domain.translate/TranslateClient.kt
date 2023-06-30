package com.plcoding.translator_kmm.translate.domain.translate

import com.plcoding.translator_kmm.core.domain.model.Language

interface ITranslateClient {
    suspend fun translate(
        fromLanguage: Language,
        toLanguage: Language,
        text: String
    ): String
}