package com.plcoding.translator_kmm.featuretranslate.data.local

import com.kmm_translator.database.TranslateDatabase
import com.plcoding.translator_kmm.Constants.DATABASE_NAME
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

actual class DatabaseDriverFactory() {
    actual fun create(): SqlDriver {
        return NativeSqliteDriver(
            TranslateDatabase.Schema, DATABASE_NAME
        )
    }
}