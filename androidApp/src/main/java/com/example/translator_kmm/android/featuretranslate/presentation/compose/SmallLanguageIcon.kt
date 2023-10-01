package com.example.translator_kmm.android.featuretranslate.presentation.compose

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.translator_kmm.android.KmmTranslatorTheme
import com.example.translator_kmm.android.core.compose.KmmTranslatorPreview
import com.example.translator_kmm.core.presentation.model.UiLanguage

@Composable
fun SmallLanguageIcon(
    modifier: Modifier = Modifier,
    @DrawableRes drawableRes: Int,
    text: String
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = drawableRes,
            contentDescription = text,
            modifier = Modifier.size(20.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = text,
            color = MaterialTheme.colors.onBackground.copy(alpha = 0.6f)
        )
    }
}

@KmmTranslatorPreview
@Composable
fun SmallLanguageIconPreview() {
    KmmTranslatorTheme {
        Column {
            SmallLanguageIcon(
                drawableRes = UiLanguage.allLanguages[0].drawableRes,
                text = UiLanguage.allLanguages[0].language.langName
            )

            Spacer(modifier = Modifier.height(16.dp))

            SmallLanguageIcon(
                drawableRes = UiLanguage.allLanguages[0].drawableRes,
                text = UiLanguage.allLanguages[0].language.langName
            )
        }
    }
}