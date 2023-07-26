package com.plcoding.translator_kmm.featuretranslate.presentation

import com.plcoding.translator_kmm.core.presentation.model.UiLanguage
import com.plcoding.translator_kmm.featuretranslate.domain.translate.TranslateError

data class TranslateState(
    val fromText: String = "",
    val toText: String? = null,
    val isTranslating: Boolean = false,
    val fromLanguage: UiLanguage = UiLanguage.languageByCode("en"),
    val toLanguage: UiLanguage = UiLanguage.languageByCode("de"),
    val isChoosingFromLanguage: Boolean = false,
    val isChoosingToLanguage: Boolean = false,
    val error: TranslateError? = null,
    val history: List<UiHistoryItem> = emptyList()
)