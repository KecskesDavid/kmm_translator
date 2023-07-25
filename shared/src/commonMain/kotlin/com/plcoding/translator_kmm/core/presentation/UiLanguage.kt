package com.plcoding.translator_kmm.core.presentation

import com.plcoding.translator_kmm.core.domain.model.Language
expect class UiLanguage {
    val language: Language

    companion object {
        val allLanguages: List<UiLanguage>
        fun languageByCode(langCode: String): UiLanguage
    }
}