package com.plcoding.translator_kmm.featuretranslate.data.local

import com.kmm_translator.database.TranslateDatabase
import com.plcoding.translator_kmm.core.domain.util.CommonFlow
import com.plcoding.translator_kmm.core.domain.util.toCommonFlow
import com.plcoding.translator_kmm.featuretranslate.data.mapper.toHistoryItem
import com.plcoding.translator_kmm.featuretranslate.domain.history.HistoryDao
import com.plcoding.translator_kmm.featuretranslate.domain.history.HistoryItem
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock

class SqlDelightHistoryDataSource(
    private val database: TranslateDatabase
) : HistoryDao {
    private val queries = database.translateQueries

    override fun getLocalHistory(): CommonFlow<List<HistoryItem>> {
        return queries
            .getLocalHistory()
            .asFlow()
            .mapToList()
            .map { history ->
                history.map { it.toHistoryItem() }
            }
            .toCommonFlow()
    }

    override suspend fun insertHistoryItem(historyItem: HistoryItem) {
        queries.insertHistoryItem(
            id = historyItem.id,
            fromLanguageCode = historyItem.fromLanguageCode,
            fromText = historyItem.fromText,
            toLanguageCode = historyItem.toLanguageCode,
            toText = historyItem.toText,
            timeStamp = Clock.System.now().toEpochMilliseconds()
        )
    }
}