package com.example.translator_kmm.featuretranslate.presentation

import com.example.translator_kmm.core.presentation.model.UiLanguage

data class UiHistoryItem(
    val id: Long,
    val fromText: String,
    val toText: String,
    val fromLanguage: UiLanguage,
    val toLanguage: UiLanguage
)