package com.plcoding.translator_kmm.featuretranslate.presentation

import com.plcoding.translator_kmm.core.presentation.model.UiLanguage

data class UiHistoryItem(
    val id: Long,
    val fromText: String,
    val toText: String,
    val fromLanguage: UiLanguage,
    val toLanguage: UiLanguage
)