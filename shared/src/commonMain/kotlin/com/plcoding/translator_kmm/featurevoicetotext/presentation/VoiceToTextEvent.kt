package com.plcoding.translator_kmm.featurevoicetotext.presentation

sealed class VoiceToTextEvent {
    object Close: VoiceToTextEvent()
    data class PermissionResults(
        val isGranted: Boolean,
        val isPermanentlyDeclined: Boolean
    ): VoiceToTextEvent()
    data class ToggleRecording(
        val languageCode: String
    ): VoiceToTextEvent()
    object Reset: VoiceToTextEvent()
}