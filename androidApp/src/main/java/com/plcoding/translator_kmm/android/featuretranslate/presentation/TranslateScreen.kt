package com.plcoding.translator_kmm.android.featuretranslate.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
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
import com.plcoding.translator_kmm.android.featuretranslate.presentation.compose.LanguagePicker
import com.plcoding.translator_kmm.core.presentation.model.UiLanguage
import com.plcoding.translator_kmm.featuretranslate.presentation.TranslateEvent
import com.plcoding.translator_kmm.featuretranslate.presentation.TranslateState

@Composable
fun TranslateScreen(
    state: TranslateState,
    onEvent: (TranslateEvent) -> Unit
) {
    Scaffold { paddingValues ->
        Box(
            modifier = Modifier.padding(
                top = 16.dp,
                bottom = paddingValues.calculateBottomPadding(),
                start = 16.dp,
                end = 16.dp
            )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                LanguagePicker(
                    selectedLanguage = state.fromLanguage,
                    allLanguages = UiLanguage.allLanguages,
                    isChoosingLanguage = state.isChoosingFromLanguage,
                    onClick = { onEvent(TranslateEvent.OpenFromLanguageDropDown) },
                    onDismiss = { onEvent(TranslateEvent.StopChoosingLanguage) },
                    onSelect = { onEvent(TranslateEvent.ChooseFromLanguage(it)) }
                )

                Spacer(modifier = Modifier.weight(1f))

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

                Spacer(modifier = Modifier.weight(1f))

                LanguagePicker(
                    selectedLanguage = state.toLanguage,
                    allLanguages = UiLanguage.allLanguages,
                    isChoosingLanguage = state.isChoosingToLanguage,
                    onClick = { onEvent(TranslateEvent.OpenToLanguageDropDown) },
                    onDismiss = { onEvent(TranslateEvent.StopChoosingLanguage) },
                    onSelect = { onEvent(TranslateEvent.ChooseToLanguage(it)) }
                )
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