package com.example.kmm_translator.featuretranslate.data.local

import com.example.translator_kmm.core.domain.util.CommonFlow
import com.example.translator_kmm.core.domain.util.toCommonFlow
import com.example.translator_kmm.featuretranslate.domain.history.HistoryDao
import com.example.translator_kmm.featuretranslate.domain.history.HistoryItem
import kotlinx.coroutines.flow.MutableStateFlow

class FakeHistoryDao: HistoryDao {
    private val _data = MutableStateFlow<List<HistoryItem>>(emptyList())
    override fun getLocalHistory(): CommonFlow<List<HistoryItem>> {
        return _data.toCommonFlow()
    }

    override suspend fun insertHistoryItem(historyItem: HistoryItem) {
        _data.value += historyItem
    }
}