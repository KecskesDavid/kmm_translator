package com.example.translator_kmm.core.presentation.model

import com.example.translator_kmm.core.domain.model.Language

actual class UiLanguage(
    val imageName: String,
    actual val language: Language
) {
    actual companion object {
        actual fun languageByCode(langCode: String): UiLanguage {
            return allLanguages.find { it.language.langCode == langCode }
                ?: throw IllegalArgumentException("Invalid or unsupported language code")
        }

        actual val allLanguages: List<UiLanguage>
            get() = Language.values().map {
                com.example.translator_kmm.core.presentation.model.UiLanguage(
                    it.langName.lowercase(),
                    it
                )
            }
    }
}