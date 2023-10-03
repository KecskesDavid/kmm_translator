package com.example.kmm_translator.featuretranslate.presentation

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import com.example.kmm_translator.featuretranslate.data.local.FakeHistoryDao
import com.example.kmm_translator.featuretranslate.data.remote.FakeTranslateClient
import com.example.translator_kmm.core.presentation.model.UiLanguage
import com.example.translator_kmm.featuretranslate.domain.history.HistoryDao
import com.example.translator_kmm.featuretranslate.domain.history.HistoryItem
import com.example.translator_kmm.featuretranslate.domain.translate.ITranslateClient
import com.example.translator_kmm.featuretranslate.domain.translate.TranslateUseCase
import com.example.translator_kmm.featuretranslate.presentation.TranslateEvent
import com.example.translator_kmm.featuretranslate.presentation.TranslateViewModel
import com.example.translator_kmm.featuretranslate.presentation.UiHistoryItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test

class TranslateViewModelTest {

    private lateinit var viewModel: TranslateViewModel
    private lateinit var client: ITranslateClient
    private lateinit var historyDao: HistoryDao

    @BeforeTest
    fun setup() {
        client = FakeTranslateClient()
        historyDao = FakeHistoryDao()
        val translateUseCase = TranslateUseCase(
            client, historyDao
        )
        viewModel = TranslateViewModel(
            translateUseCase,
            historyDao,
            CoroutineScope((Dispatchers.Default))
        )
    }

    @Test
    fun `State and history items are properly combined when new item added`() = runBlocking {
        viewModel.state.test {
            // arrange
            awaitItem()
            val item = HistoryItem(
                id = 0,
                fromLanguageCode = "en",
                fromText = "from",
                toLanguageCode = "de",
                toText = "to"
            )

            // act
            historyDao.insertHistoryItem(item)

            val state = awaitItem()

            // assert
            assertThat(state.history.first()).isEqualTo(
                UiHistoryItem(
                    id = item.id!!,
                    fromText = item.fromText,
                    toText = item.toText,
                    fromLanguage = UiLanguage.languageByCode(item.fromLanguageCode),
                    toLanguage = UiLanguage.languageByCode(item.toLanguageCode)
                )
            )
        }
    }

    @Test
    fun `State is updated properly when Translate is successfull`() = runBlocking {
        viewModel.state.test {
            awaitItem()

            viewModel.onEvent(TranslateEvent.ChangeTranslationText("test"))
            awaitItem()

            viewModel.onEvent(TranslateEvent.Translate)

            val loadingState = awaitItem()
            assertThat(loadingState.isTranslating).isTrue()

            val resultState = awaitItem()
            assertThat(resultState.isTranslating).isFalse()
            assertThat(resultState.toText).isEqualTo(FakeTranslateClient.TRANSLATED_TEXT)
        }
    }

}