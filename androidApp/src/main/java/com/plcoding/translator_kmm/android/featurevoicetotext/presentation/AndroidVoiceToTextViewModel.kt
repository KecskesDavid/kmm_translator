package com.plcoding.translator_kmm.android.featurevoicetotext.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.translator_kmm.featurevoicetotext.domain.IVoiceToTextParser
import com.plcoding.translator_kmm.featurevoicetotext.presentation.VoiceToTextEvent
import com.plcoding.translator_kmm.featurevoicetotext.presentation.VoiceToTextViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AndroidVoiceToTextViewModel @Inject constructor(
    private val parser: IVoiceToTextParser
) : ViewModel() {
    private val viewModel by lazy {
        VoiceToTextViewModel(
            parser = parser,
            coroutineScope = viewModelScope
        )
    }

    val state = viewModel.state

    fun onEvent(event: VoiceToTextEvent) {
        viewModel.onEvent(event)
    }
}
