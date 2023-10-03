package com.example.kmm_translator.di

import com.example.kmm_translator.featuretranslate.data.FakeVoiceToTextParser
import com.example.kmm_translator.featurevoicetotext.data.local.FakeHistoryDao
import com.example.kmm_translator.featurevoicetotext.data.remote.FakeTranslateClient
import com.example.translator_kmm.featuretranslate.domain.history.HistoryDao
import com.example.translator_kmm.featuretranslate.domain.translate.ITranslateClient
import com.example.translator_kmm.featuretranslate.domain.translate.TranslateUseCase
import com.example.translator_kmm.featurevoicetotext.domain.IVoiceToTextParser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Provides
    @Singleton
    fun provideFakeTranslateClient(): ITranslateClient {
        return FakeTranslateClient()
    }

    @Provides
    @Singleton
    fun provideFakeHistoryDataSource(): HistoryDao {
        return FakeHistoryDao()
    }

    @Provides
    @Singleton
    fun provideTranslateUseCase(
        client: ITranslateClient,
        dataSource: HistoryDao
    ): TranslateUseCase {
        return TranslateUseCase(client, dataSource)
    }

    @Provides
    @Singleton
    fun provideFakeVoiceToTextParser(): IVoiceToTextParser {
        return FakeVoiceToTextParser()
    }
}