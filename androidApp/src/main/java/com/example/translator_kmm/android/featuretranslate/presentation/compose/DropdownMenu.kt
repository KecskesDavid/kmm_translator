package com.example.translator_kmm.android.featuretranslate.presentation.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.translator_kmm.android.KmmTranslatorTheme
import com.example.translator_kmm.core.presentation.model.UiLanguage

@Composable
fun DropdownMenu(
    expanded: Boolean = false,
    items: List<UiLanguage>,
    selectedIndex: Int = -1,
    onItemSelected: (index: Int, item: UiLanguage) -> Unit,
    onDismissDropdown: () -> Unit
) {
    if (expanded) {
        Dialog(
            onDismissRequest = { onDismissDropdown() }
        ) {
            KmmTranslatorTheme {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                ) {
                    val listState = rememberLazyListState()
                    if (selectedIndex > -1) {
                        LaunchedEffect("ScrollToSelected") {
                            listState.scrollToItem(index = selectedIndex)
                        }
                    }

                    LazyColumn(modifier = Modifier.fillMaxWidth(), state = listState) {
                        itemsIndexed(items) { index, item ->
                            DropdownMenuItem(
                                item,
                                true
                            ) { onItemSelected(index, item) }

                            if (index < items.lastIndex) {
                                Divider(modifier = Modifier.padding(horizontal = 16.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DropdownMenuItem(
    language: UiLanguage,
    enabled: Boolean,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .clickable(enabled) { onClick() }
            .fillMaxWidth()
            .height(60.dp)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = language.drawableRes),
            contentDescription = language.language.langName,
            modifier = Modifier.size(30.dp),
            tint = Color.Unspecified
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = language.language.langName
        )
    }
}