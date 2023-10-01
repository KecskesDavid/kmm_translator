package com.example.translator_kmm.featuretranslate.domain.history

import com.example.translator_kmm.core.domain.util.CommonFlow

interface HistoryDao {
    fun getLocalHistory(): CommonFlow<List<HistoryItem>>
    suspend fun insertHistoryItem(historyItem: HistoryItem)
}