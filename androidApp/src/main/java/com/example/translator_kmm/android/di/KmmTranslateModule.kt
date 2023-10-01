package com.example.translator_kmm.android.di

import android.app.Application
import com.kmm_translator.database.TranslateDatabase
import com.example.translator_kmm.featuretranslate.data.client.KtorTranslateClient
import com.example.translator_kmm.featuretranslate.data.local.DatabaseDriverFactory
import com.example.translator_kmm.featuretranslate.data.local.SqlDelightHistoryDao
import com.example.translator_kmm.featuretranslate.data.remote.HttpClientFactory
import com.example.translator_kmm.featuretranslate.domain.history.HistoryDao
import com.example.translator_kmm.featuretranslate.domain.translate.ITranslateClient
import com.example.translator_kmm.featuretranslate.domain.translate.TranslateUseCase
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object KmmTranslateModule {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClientFactory().create()
    }

    @Provides
    @Singleton
    fun provideTranslateClient(httpClient: HttpClient): ITranslateClient {
        return KtorTranslateClient(httpClient)
    }

    @Provides
    @Singleton
    fun provideDatabaseDriver(app: Application): SqlDriver {
        return DatabaseDriverFactory(app).create()
    }

    @Provides
    @Singleton
    fun provideHistoryDao(driver: SqlDriver): HistoryDao {
        return SqlDelightHistoryDao(TranslateDatabase(driver))
    }

    @Provides
    @Singleton
    fun provideTranslateUseCase(
        client: ITranslateClient,
        dataSource: HistoryDao
    ): TranslateUseCase {
        return TranslateUseCase(client, dataSource)
    }
}