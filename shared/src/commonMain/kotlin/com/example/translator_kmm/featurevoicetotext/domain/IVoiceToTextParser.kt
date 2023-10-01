package com.example.translator_kmm.featurevoicetotext.domain

import com.example.translator_kmm.core.domain.util.CommonStateFlow

interface IVoiceToTextParser {
    val state: CommonStateFlow<VoiceToTextParserState>
    fun startListening(languageCode: String) // From language code
    fun stopListening()
    fun cancelListening()
    fun reset()
}