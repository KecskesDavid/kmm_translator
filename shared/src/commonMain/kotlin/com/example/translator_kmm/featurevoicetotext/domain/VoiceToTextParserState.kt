package com.example.translator_kmm.featurevoicetotext.domain

data class VoiceToTextParserState(
    val result: String = "",
    val error: String? = null,
    // How loud is the user talking -> so we can draw the bars on the UI
    // Between 0f-1f
    val powerRatio: Float = 0f,
    val isSpeaking: Boolean = false
) {
    companion object {
        val DEFAULT_STATE = VoiceToTextParserState()
    }
}