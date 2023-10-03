package com.example.kmm_translator.featuretranslate.data

import com.example.translator_kmm.core.domain.util.CommonStateFlow
import com.example.translator_kmm.core.domain.util.toCommonStateFlow
import com.example.translator_kmm.featurevoicetotext.domain.IVoiceToTextParser
import com.example.translator_kmm.featurevoicetotext.domain.VoiceToTextParserState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class FakeVoiceToTextParser: IVoiceToTextParser {
    private val _state = MutableStateFlow(VoiceToTextParserState())
    override val state: CommonStateFlow<VoiceToTextParserState>
        get() = _state.toCommonStateFlow()

    var voiceResult = "test result"

    override fun startListening(languageCode: String) {
        _state.update { it.copy(
            result = "",
            isSpeaking = true
        ) }
    }

    override fun stopListening() {
        _state.update { it.copy(
            result =  voiceResult,
            isSpeaking = false
        ) }
    }

    override fun cancelListening() = Unit

    override fun reset() {
        _state.update { VoiceToTextParserState() }
    }
}