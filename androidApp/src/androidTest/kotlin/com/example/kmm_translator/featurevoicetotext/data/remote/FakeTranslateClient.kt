package com.example.kmm_translator.featurevoicetotext.data.remote

import com.example.translator_kmm.core.domain.model.Language
import com.example.translator_kmm.featuretranslate.domain.translate.ITranslateClient

class FakeTranslateClient: ITranslateClient {

    companion object {
        const val TRANSLATED_TEXT = "TRANSLATED_TEXT"
    }

    override suspend fun translate(
        fromLanguage: Language,
        toLanguage: Language,
        text: String
    ): String {
        return TRANSLATED_TEXT
    }
}