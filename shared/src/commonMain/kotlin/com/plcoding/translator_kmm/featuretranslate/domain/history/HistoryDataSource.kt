package com.plcoding.translator_kmm.featuretranslate.domain.history

import com.plcoding.translator_kmm.core.domain.util.CommonFlow

interface HistoryDataSource {
    fun getLocalHistory(): CommonFlow<List<HistoryItem>>
    suspend fun insertHistoryItem(historyItem: HistoryItem)
}