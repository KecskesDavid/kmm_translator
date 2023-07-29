package com.plcoding.translator_kmm.android.featuretranslate.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FabPosition
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.plcoding.translator_kmm.android.KmmTranslatorTheme
import com.plcoding.translator_kmm.android.R
import com.plcoding.translator_kmm.android.core.compose.KmmTranslatorPreview
import com.plcoding.translator_kmm.android.featuretranslate.presentation.compose.HistorySection
import com.plcoding.translator_kmm.android.featuretranslate.presentation.compose.LanguagePicker
import com.plcoding.translator_kmm.android.featuretranslate.presentation.compose.TranslateTextField
import com.plcoding.translator_kmm.core.presentation.model.UiLanguage
import com.plcoding.translator_kmm.featuretranslate.presentation.TranslateEvent
import com.plcoding.translator_kmm.featuretranslate.presentation.TranslateState
import com.plcoding.translator_kmm.featuretranslate.presentation.UiHistoryItem

@Composable
fun TranslateScreen(
    state: TranslateState,
    onEvent: (TranslateEvent) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            IconButton(
                onClick = { },
                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape)
                    .padding(0.dp)
                    .background(color = MaterialTheme.colors.primary)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.mic),
                    contentDescription = "Navigate to Speech Recognition",
                    tint = Color.Unspecified
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { paddingValues ->
        Box(
            modifier = Modifier.padding(
                top = 16.dp,
                bottom = paddingValues.calculateBottomPadding(),
                start = 16.dp,
                end = 16.dp
            )
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    LanguagePicker(
                        selectedLanguage = state.fromLanguage,
                        allLanguages = UiLanguage.allLanguages,
                        isChoosingLanguage = state.isChoosingFromLanguage,
                        onClick = { onEvent(TranslateEvent.OpenFromLanguageDropDown) },
                        onDismiss = { onEvent(TranslateEvent.StopChoosingLanguage) },
                        onSelect = { onEvent(TranslateEvent.ChooseFromLanguage(it)) }
                    )

                    IconButton(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(MaterialTheme.colors.primary),
                        onClick = { onEvent(TranslateEvent.SwapLanguages) }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.swap_languages),
                            contentDescription = "Swap languages Icon",
                            tint = Color.Unspecified
                        )
                    }

                    LanguagePicker(
                        selectedLanguage = state.toLanguage,
                        allLanguages = UiLanguage.allLanguages,
                        isChoosingLanguage = state.isChoosingToLanguage,
                        onClick = { onEvent(TranslateEvent.OpenToLanguageDropDown) },
                        onDismiss = { onEvent(TranslateEvent.StopChoosingLanguage) },
                        onSelect = { onEvent(TranslateEvent.ChooseToLanguage(it)) }
                    )
                }

                TranslateTextField(
                    modifier = Modifier.padding(vertical = 16.dp),
                    fromText = state.fromText,
                    toText = state.toText,
                    fromLanguage = state.fromLanguage,
                    toLanguage = state.toLanguage,
                    isTranslating = state.isTranslating,
                    isInIdleState = state.toText.isNullOrBlank(),
                    onTextChange = { onEvent(TranslateEvent.ChangeTranslationText(it)) },
                    onTranslateClick = { onEvent(TranslateEvent.Translate) },
                    onCloseTranslation = { onEvent(TranslateEvent.CloseTranslation) }
                )

                HistorySection(historyList = listOf(
                    UiHistoryItem(
                        id = 0L,
                        fromText = "Somethings",
                        fromLanguage = UiLanguage.allLanguages[0],
                        toText = "Some more things",
                        toLanguage = UiLanguage.allLanguages[0],
                    ),
                    UiHistoryItem(
                        id = 0L,
                        fromText = "Somethings",
                        fromLanguage = UiLanguage.allLanguages[0],
                        toText = "Some more things",
                        toLanguage = UiLanguage.allLanguages[0],
                    )
                ), onHistoryItemClick = {})
            }
        }
    }
}

@KmmTranslatorPreview
@Composable
fun TranslateScreenPreview() {
    KmmTranslatorTheme {
        TranslateScreen(state = TranslateState(), onEvent = {})
    }
}