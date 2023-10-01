package com.example.translator_kmm.android.featuretranslate.presentation.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.translator_kmm.android.KmmTranslatorTheme
import com.example.translator_kmm.android.R
import com.example.translator_kmm.android.core.compose.KmmTranslatorPreview
import com.example.translator_kmm.android.theme.LightBlue
import com.example.translator_kmm.core.presentation.model.UiLanguage

@Composable
fun LanguagePicker(
    modifier: Modifier = Modifier,
    selectedLanguage: UiLanguage,
    allLanguages: List<UiLanguage>,
    isChoosingLanguage: Boolean = false,
    onClick: () -> Unit,
    onDismiss: () -> Unit,
    onSelect: (UiLanguage) -> Unit
) {
    Box(modifier = modifier) {
        DropdownMenu(
            items = allLanguages,
            expanded = isChoosingLanguage,
            onItemSelected = { _, language -> onSelect(language) },
            onDismissDropdown = { onDismiss() },
        )

        Row(
            modifier = modifier.clickable { onClick() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = selectedLanguage.drawableRes,
                contentDescription = selectedLanguage.language.langName,
                modifier = Modifier.size(30.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = selectedLanguage.language.langName,
                color = MaterialTheme.colors.onBackground.copy(alpha = 0.6f)
            )

            Icon(
                imageVector = if (isChoosingLanguage) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                contentDescription = if (isChoosingLanguage) {
                    stringResource(id = R.string.close)
                } else {
                    stringResource(id = R.string.open)
                },
                tint = LightBlue,
                modifier = Modifier.size(30.dp)
            )
        }
    }
}

@KmmTranslatorPreview
@Composable
fun LanguagePickerPreview() {
    KmmTranslatorTheme {
        LanguagePicker(
            selectedLanguage = UiLanguage.allLanguages[0],
            allLanguages = UiLanguage.allLanguages,
            onClick = {},
            onDismiss = {},
            onSelect = {}
        )
    }
}

@KmmTranslatorPreview
@Composable
fun LanguagePickerPreviewExpanded() {
    KmmTranslatorTheme {
        LanguagePicker(
            selectedLanguage = UiLanguage.allLanguages[0],
            allLanguages = UiLanguage.allLanguages,
            isChoosingLanguage = true,
            onClick = {},
            onDismiss = {},
            onSelect = {}
        )
    }
}