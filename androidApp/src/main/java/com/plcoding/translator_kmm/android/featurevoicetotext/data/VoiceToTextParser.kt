package com.plcoding.translator_kmm.android.featurevoicetotext.data

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import com.plcoding.translator_kmm.core.domain.util.CommonStateFlow
import com.plcoding.translator_kmm.core.domain.util.toCommonStateFlow
import com.plcoding.translator_kmm.featurevoicetotext.domain.IVoiceToTextParser
import com.plcoding.translator_kmm.featurevoicetotext.domain.VoiceToTextParserState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class VoiceToTextParser(
    private val app: Application
) : IVoiceToTextParser, RecognitionListener {

    private val _recognizer = SpeechRecognizer.createSpeechRecognizer(app)

    private val _state = MutableStateFlow(VoiceToTextParserState.DEFAULT_STATE)
    override val state: CommonStateFlow<VoiceToTextParserState>
        get() = _state.toCommonStateFlow()

    override fun startListening(languageCode: String) {
        _state.update { VoiceToTextParserState() }

        if (!SpeechRecognizer.isRecognitionAvailable(app)) {
            _state.update {
                it.copy(
                    error = "Speech recognition not available at the moment"
                )
            }
            return
        }

        val intent = Intent(
            RecognizerIntent.ACTION_RECOGNIZE_SPEECH
        ).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, languageCode)
        }

        _recognizer.setRecognitionListener(this)
        _recognizer.startListening(intent)

        _state.update {
            it.copy(
                isSpeaking = true
            )
        }
    }

    override fun stopListening() {
        _state.update { VoiceToTextParserState.DEFAULT_STATE }
        _recognizer.stopListening()
    }

    override fun cancelListening() {
        _recognizer.stopListening()
    }

    override fun reset() {
        _state.update { VoiceToTextParserState.DEFAULT_STATE }
    }

    override fun onReadyForSpeech(params: Bundle?) {
        _state.update { it.copy(error = null) }
    }

    override fun onBeginningOfSpeech() = Unit

    override fun onRmsChanged(rmsdB: Float) {
        // Normalize rmsdB -> 0f <= rmsdB <= 1f
        _state.update { it.copy(powerRation = rmsdB * (1f / (12f - (-2f)))) }
    }

    override fun onBufferReceived(buffer: ByteArray?) = Unit

    override fun onEndOfSpeech() {
        _state.update { it.copy(isSpeaking = false) }
    }

    override fun onError(error: Int) {
        _state.update { it.copy(error = "Error: $error") }
    }

    override fun onResults(results: Bundle?) {
        results
            ?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
            ?.getOrNull(0)
            ?.let { result ->
                _state.update {
                    it.copy(
                        result
                    )
                }
            }
    }

    override fun onPartialResults(partialResults: Bundle?) = Unit

    override fun onEvent(eventType: Int, params: Bundle?) = Unit
}