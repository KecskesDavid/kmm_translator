package com.plcoding.translator_kmm.translate.data.client

import com.plcoding.translator_kmm.Constants
import com.plcoding.translator_kmm.core.domain.model.Language
import com.plcoding.translator_kmm.translate.data.entity.TranslateRequest
import com.plcoding.translator_kmm.translate.data.entity.TranslateResponse
import com.plcoding.translator_kmm.translate.domain.translate.ITranslateClient
import com.plcoding.translator_kmm.translate.domain.translate.TranslateError
import com.plcoding.translator_kmm.translate.domain.translate.TranslateException
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.utils.io.errors.IOException

class KtorTranslateClient(
    private val httpClient: HttpClient
) : ITranslateClient {
    override suspend fun translate(
        fromLanguage: Language,
        toLanguage: Language,
        text: String
    ): String {
        val result = try {
            httpClient.post {
                url(Constants.BASE_URL + "/translate")
                setBody(
                    TranslateRequest(
                        textToTranslate = text,
                        sourceLanguageCode = fromLanguage.langCode,
                        targetLanguageCode = toLanguage.langCode
                    )
                )
            }
        } catch (e: IOException) {
            throw TranslateException(TranslateError.SERVICE_UNAVAILABLE)
        }

        checkStatus(result.status.value)

        return try {
            result.body<TranslateResponse>().translatedText
        } catch (e: Exception) {
            throw TranslateException(TranslateError.SERVER_ERROR)
        }
    }

    private fun checkStatus(value: Int) {
        when (value) {
            500 -> throw TranslateException(TranslateError.SERVER_ERROR)
            in 400..499 -> throw TranslateException(TranslateError.CLIENT_ERROR)
            else -> throw TranslateException(TranslateError.UNKNOWN_ERROR)
        }
    }
}