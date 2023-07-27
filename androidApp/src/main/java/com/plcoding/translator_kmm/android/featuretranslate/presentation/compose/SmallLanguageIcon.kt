package com.plcoding.translator_kmm.android.featuretranslate.presentation.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.plcoding.translator_kmm.android.KmmTranslatorTheme
import com.plcoding.translator_kmm.android.core.compose.KmmTranslatorPreview
import com.plcoding.translator_kmm.core.presentation.model.UiLanguage

@Composable
fun SmallLanguageIcon(
    language: UiLanguage,
    showLanguageName: Boolean = true,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = language.drawableRes,
            contentDescription = language.language.langName,
            modifier = Modifier.size(20.dp)
        )

        if (showLanguageName) {
            Spacer(modifier = Modifier.width(8.dp))

            Text(text = language.language.langName)
        }
    }
}

@KmmTranslatorPreview
@Composable
fun SmallLanguageIconPreview() {
    KmmTranslatorTheme {
        Column {
            SmallLanguageIcon(language = UiLanguage.allLanguages[0])

            Spacer(modifier = Modifier.height(16.dp))

            SmallLanguageIcon(language = UiLanguage.allLanguages[1])

            Spacer(modifier = Modifier.height(16.dp))

            SmallLanguageIcon(language = UiLanguage.allLanguages[2], showLanguageName = false)
        }
    }
}