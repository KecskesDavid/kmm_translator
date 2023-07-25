package com.plcoding.translator_kmm.featuretranslate.domain.translate

import com.plcoding.translator_kmm.core.domain.model.Language
import com.plcoding.translator_kmm.core.domain.util.Resource
import com.plcoding.translator_kmm.featuretranslate.domain.history.HistoryDataSource
import com.plcoding.translator_kmm.featuretranslate.domain.history.HistoryItem

class TranslateUseCase(
    private val translateClient: ITranslateClient,
    private val historyDataSource: HistoryDataSource
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

            historyDataSource.insertHistoryItem(
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