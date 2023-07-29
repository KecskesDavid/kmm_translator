package com.plcoding.translator_kmm.android.featuretranslate.presentation.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.plcoding.translator_kmm.android.KmmTranslatorTheme
import com.plcoding.translator_kmm.android.core.compose.KmmTranslatorPreview
import com.plcoding.translator_kmm.android.core.compose.util.gradientSurface
import com.plcoding.translator_kmm.core.presentation.model.UiLanguage
import com.plcoding.translator_kmm.featuretranslate.presentation.UiHistoryItem

@Composable
fun HistorySection(
    modifier: Modifier = Modifier,
    historyList: List<UiHistoryItem>,
    onHistoryItemClick: (UiHistoryItem) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "History",
            style = MaterialTheme.typography.h1
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn {
            items(historyList) { historyItem ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(20.dp))
                        .gradientSurface()
                        .padding(12.dp)
                        .clickable { onHistoryItemClick(historyItem) }
                ) {
                    SmallLanguageIcon(
                        drawableRes = historyItem.fromLanguage.drawableRes,
                        text = historyItem.fromText
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    SmallLanguageIcon(
                        drawableRes = historyItem.toLanguage.drawableRes,
                        text = historyItem.fromText
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@KmmTranslatorPreview
@Composable
fun HistorySectionPreview() {
    KmmTranslatorTheme {
        HistorySection(
            historyList = listOf(
                UiHistoryItem(
                    id = 0L,
                    fromText = "Somethings",
                    fromLanguage = UiLanguage.allLanguages[1],
                    toText = "Some more things",
                    toLanguage = UiLanguage.allLanguages[2],
                ),
                UiHistoryItem(
                    id = 0L,
                    fromText = "Somethings",
                    fromLanguage = UiLanguage.allLanguages[1],
                    toText = "Some more things",
                    toLanguage = UiLanguage.allLanguages[2],
                )
            ),
            onHistoryItemClick = {}
        )
    }
}