package com.plcoding.translator_kmm.featurevoicetotext.presentation

data class VoiceToTextState(
    val powerRatios: List<Float> = emptyList(),
    val spokenText: String = "",
    val canSpeak: Boolean = false,
    val error: String? = null,
    val displayState: DisplayState? = null
)

enum class DisplayState {
    WAIT_TO_TALK,
    DISPLAY_RESULTS,
    SPEAKING,
    ERROR
}
