package com.plcoding.translator_kmm.featuretranslate.presentation

import com.plcoding.translator_kmm.core.domain.util.Resource
import com.plcoding.translator_kmm.core.domain.util.toCommonStateFlow
import com.plcoding.translator_kmm.core.presentation.model.UiLanguage
import com.plcoding.translator_kmm.featuretranslate.domain.history.HistoryDao
import com.plcoding.translator_kmm.featuretranslate.domain.translate.TranslateException
import com.plcoding.translator_kmm.featuretranslate.domain.translate.TranslateUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.updateAndGet
import kotlinx.coroutines.launch

class TranslateViewModel(
    private val translateUseCase: TranslateUseCase,
    private val historyDao: HistoryDao,
    private val coroutineScope: CoroutineScope? = null
) {
    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)
    private var translateJob: Job? = null

    private val _state = MutableStateFlow(TranslateState())
    val state = combine(
        _state,
        historyDao.getLocalHistory()
    ) { state, history ->
        if (state.history != history) {
            state.copy(
                history = history.mapNotNull {
                    UiHistoryItem(
                        id = it.id ?: return@mapNotNull null,
                        fromText = it.fromText,
                        toText = it.toText,
                        fromLanguage = UiLanguage.languageByCode(it.fromLanguageCode),
                        toLanguage = UiLanguage.languageByCode(it.toLanguageCode),
                    )
                }
            )
        } else state
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TranslateState())
        .toCommonStateFlow()

    fun onEvent(event: TranslateEvent) {
        when (event) {
            is TranslateEvent.ChangeTranslationText ->
                _state.update {
                    it.copy(fromText = event.text)
                }

            is TranslateEvent.ChooseFromLanguage -> {
                val newState = _state.updateAndGet {
                    it.copy(
                        isChoosingFromLanguage = false,
                        fromLanguage = event.language
                    )
                }
                translate(newState)
            }

            is TranslateEvent.ChooseToLanguage -> {
                val newState = _state.updateAndGet {
                    it.copy(
                        isChoosingToLanguage = false,
                        toLanguage = event.language
                    )
                }
                translate(newState)
            }

            TranslateEvent.CloseTranslation ->
                _state.update {
                    it.copy(
                        isTranslating = false,
                        fromText = "",
                        toText = null
                    )
                }

            TranslateEvent.EditTranslation ->
                if (_state.value.toText != null) {
                    _state.update {
                        it.copy(
                            isTranslating = false,
                            toText = null
                        )
                    }
                }

            TranslateEvent.OnErrorSeen -> {
                _state.update {
                    it.copy(
                        error = null
                    )
                }
            }

            TranslateEvent.OpenFromLanguageDropDown ->
                _state.update {
                    it.copy(isChoosingFromLanguage = true)
                }

            TranslateEvent.OpenToLanguageDropDown ->
                _state.update {
                    it.copy(isChoosingToLanguage = true)
                }

            is TranslateEvent.SelectHistoryItem -> {
                translateJob?.cancel()
                _state.update {
                    it.copy(
                        fromText = event.item.fromText,
                        toText = event.item.toText,
                        isTranslating = false,
                        fromLanguage = event.item.fromLanguage,
                        toLanguage = event.item.toLanguage
                    )
                }
            }

            TranslateEvent.StopChoosingLanguage ->
                _state.update {
                    it.copy(
                        isChoosingFromLanguage = false,
                        isChoosingToLanguage = false
                    )
                }

            is TranslateEvent.SubmitVoiceResult ->
                _state.update {
                    it.copy(
                        fromText = event.result.toString(),
                        isTranslating = if (event.result != null) false else it.isTranslating,
                        toText = if (event.result != null) null else it.toText
                    )
                }

            TranslateEvent.SwapLanguages ->
                _state.update {
                    it.copy(
                        fromLanguage = it.toLanguage,
                        toLanguage = it.fromLanguage,
                        fromText = it.toText ?: "",
                        toText = it.fromText
                    )
                }

            TranslateEvent.Translate -> translate(_state.value)

            else -> Unit
        }
    }

    private fun translate(state: TranslateState) {
        if (state.isTranslating || state.fromText.isBlank()) return

        translateJob = viewModelScope.launch {
            _state.update {
                it.copy(isTranslating = true)
            }

            val result = translateUseCase(
                fromLanguage = state.fromLanguage.language,
                toLanguage = state.toLanguage.language,
                text = state.fromText,
            )

            when (result) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            isTranslating = false,
                            toText = result.data.toString()
                        )
                    }
                }

                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            isTranslating = false,
                            error = (result.error as? TranslateException)?.error
                        )
                    }
                }
            }
        }
    }
}