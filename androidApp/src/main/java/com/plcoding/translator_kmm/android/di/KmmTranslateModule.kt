package com.plcoding.translator_kmm.android.di

import android.app.Application
import com.plcoding.translator_kmm.featuretranslate.data.client.KtorTranslateClient
import com.plcoding.translator_kmm.featuretranslate.data.local.DatabaseDriverFactory
import com.plcoding.translator_kmm.featuretranslate.data.remote.HttpClientFactory
import com.plcoding.translator_kmm.featuretranslate.domain.history.HistoryDao
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
    fun provideTranslateClient(httpClient: HttpClient): TranslateClient {
        return KtorTranslateClient(httpClient)
    }

    @Provides
    @Singleton
    fun provideDatabaseDriver(app: Application): SqlDriver {
        return DatabaseDriverFactory(app).create()
    }

    @Provides
    @Singleton
    fun provideHistoryDataSource(driver: SqlDriver): HistoryDao {
        return SqlDelightHistoryDataSource(TranslateDatabase(driver))
    }

    @Provides
    @Singleton
    fun provideTranslateUseCase(
        client: TranslateClient,
        dataSource: HistoryDao
    ): Translate {
        return Translate(client, dataSource)
    }
}