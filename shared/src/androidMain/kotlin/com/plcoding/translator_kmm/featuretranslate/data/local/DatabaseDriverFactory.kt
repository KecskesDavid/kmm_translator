package com.plcoding.translator_kmm.featuretranslate.data.local

import android.content.Context
import com.kmm_translator.database.TranslateDatabase
import com.plcoding.translator_kmm.Constants.DATABASE_NAME
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

actual class DatabaseDriverFactory(
    private val context: Context
) {
    actual fun create(): SqlDriver {
        return AndroidSqliteDriver(
            TranslateDatabase.Schema,
            context,
            DATABASE_NAME
        )
    }
}