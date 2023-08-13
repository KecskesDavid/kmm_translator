package com.plcoding.translator_kmm.di

import com.kmm_translator.database.TranslateDatabase
import com.plcoding.translator_kmm.featuretranslate.data.client.KtorTranslateClient
import com.plcoding.translator_kmm.featuretranslate.data.local.DatabaseDriverFactory
import com.plcoding.translator_kmm.featuretranslate.data.local.SqlDelightHistoryDao
import com.plcoding.translator_kmm.featuretranslate.data.remote.HttpClientFactory
import com.plcoding.translator_kmm.featuretranslate.domain.translate.ITranslateClient
import com.plcoding.translator_kmm.featuretranslate.domain.translate.TranslateUseCase
import io.ktor.client.HttpClient

class AppModule {
    val historyDataSource: SqlDelightHistoryDao by lazy {
        SqlDelightHistoryDao(
            TranslateDatabase(
                DatabaseDriverFactory().create()
            )
        )
    }

    val translateUseCase: TranslateUseCase by lazy {
        TranslateUseCase(translateClient, historyDataSource)
    }

    private val translateClient: ITranslateClient by lazy {
        KtorTranslateClient(
            HttpClientFactory().create()
        )
    }
}