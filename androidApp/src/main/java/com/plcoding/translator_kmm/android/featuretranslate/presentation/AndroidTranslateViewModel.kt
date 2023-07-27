package com.plcoding.translator_kmm.android.featuretranslate.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.translator_kmm.featuretranslate.domain.history.HistoryDao
import com.plcoding.translator_kmm.featuretranslate.domain.translate.TranslateUseCase
import com.plcoding.translator_kmm.featuretranslate.presentation.TranslateEvent
import com.plcoding.translator_kmm.featuretranslate.presentation.TranslateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AndroidTranslateViewModel @Inject constructor(
    private val translateUseCase: TranslateUseCase,
    private val historyDao: HistoryDao
): ViewModel() {

    private val viewModel by lazy {
        TranslateViewModel(
            translateUseCase,
            historyDao,
            viewModelScope
        )
    }

    val state = viewModel.state

    fun onEvent(event: TranslateEvent) {
        viewModel.onEvent(event)
    }
}