package com.example.translator_kmm.featuretranslate.data.mapper

import com.example.translator_kmm.featuretranslate.domain.history.HistoryItem
import database.HistoryTable

fun HistoryTable.toHistoryItem(): HistoryItem {
    return HistoryItem(
        id = id,
        fromLanguageCode = fromLanguageCode,
        fromText = fromText,
        toLanguageCode = toLanguageCode,
        toText = toText
    )
}