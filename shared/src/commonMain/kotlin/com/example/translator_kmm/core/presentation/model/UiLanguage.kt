package com.example.translator_kmm.core.presentation.model

import com.example.translator_kmm.core.domain.model.Language

expect class UiLanguage {
    val language: Language

    companion object {
        val allLanguages: List<UiLanguage>
        fun languageByCode(langCode: String): UiLanguage
    }
}