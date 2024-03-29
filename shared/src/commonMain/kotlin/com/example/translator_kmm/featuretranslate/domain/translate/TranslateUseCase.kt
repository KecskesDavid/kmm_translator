package com.example.translator_kmm.featuretranslate.domain.translate

import com.example.translator_kmm.core.domain.model.Language
import com.example.translator_kmm.core.domain.util.Resource
import com.example.translator_kmm.featuretranslate.domain.history.HistoryDao
import com.example.translator_kmm.featuretranslate.domain.history.HistoryItem

class TranslateUseCase(
    private val translateClient: ITranslateClient,
    private val historyDao: HistoryDao
) {
    suspend operator fun invoke(
        fromLanguage: Language,
        toLanguage: Language,
        text: String
    ): Resource<String> {
        try {
            val translatedText = translateClient.translate(
                fromLanguage, toLanguage, text
            )

            historyDao.insertHistoryItem(
                HistoryItem(
                    id = null,
                    fromLanguageCode = fromLanguage.langCode,
                    fromText = text,
                    toLanguageCode = toLanguage.langCode,
                    toText = translatedText
                )
            )

            return Resource.Success(translatedText)
        } catch (e: Exception) {
            e.printStackTrace()
            return Resource.Error(e)
        }
    }
}