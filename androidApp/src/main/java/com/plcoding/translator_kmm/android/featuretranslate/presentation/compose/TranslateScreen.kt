package com.plcoding.translator_kmm.android.featuretranslate.presentation.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.plcoding.translator_kmm.core.presentation.model.UiLanguage
import com.plcoding.translator_kmm.featuretranslate.presentation.TranslateEvent
import com.plcoding.translator_kmm.featuretranslate.presentation.TranslateState

@Composable
fun TranslateScreen(
    state: TranslateState,
    onEvent: (TranslateEvent) -> Unit
) {
    Scaffold { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                LanguagePicker(
                    selectedLanguage = state.fromLanguage,
                    allLanguages = UiLanguage.allLanguages,
                    onClick = { },
                    onDismiss = { },
                    onSelect = { }
                )
            }
        }
    }
}