package com.plcoding.translator_kmm.android.featuretranslate.presentation.compose

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.plcoding.translator_kmm.android.KmmTranslatorTheme
import com.plcoding.translator_kmm.android.core.compose.KmmTranslatorPreview
import com.plcoding.translator_kmm.android.core.compose.util.gradientSurface

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TranslateTextField(
    modifier: Modifier = Modifier,
    text: String = "",
    isInIdleState: Boolean = true,
    isTranslating: Boolean = false,
    onTranslateClick: () -> Unit,
    onTextChange: (String) -> Unit
) {
    if (isInIdleState) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(20.dp))
                .gradientSurface()
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                value = text,
                onValueChange = onTextChange,
                minLines = 6,
                visualTransformation = VisualTransformation.None,
                placeholder = { Text(text = "Please enter your text") },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = MaterialTheme.colors.onBackground,
                    focusedIndicatorColor = Color.Unspecified,
                    unfocusedIndicatorColor = Color.Unspecified,
                    backgroundColor = Color.Transparent
                )
            )

            Button(
                onClick = onTranslateClick,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(16.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.primary
                ),
                shape = RoundedCornerShape(100)
            ) {
                AnimatedContent(targetState = isTranslating) { isLoading ->
                    if (isLoading) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colors.onPrimary,
                            strokeWidth = 3.dp
                        )
                    } else {
                        Text(
                            text = "Translate",
                            color = MaterialTheme.colors.onPrimary,
                        )
                    }
                }
            }
        }
    } else {

    }
}

@KmmTranslatorPreview
@Composable
fun TranslateTextFieldPreviewIdleState() {
    KmmTranslatorTheme {
        TranslateTextField(text = "This is a sample text", onTranslateClick = {}, onTextChange = {})
    }
}

@KmmTranslatorPreview
@Composable
fun TranslateTextFieldPreviewWithLoadingButton() {
    KmmTranslatorTheme {
        TranslateTextField(isTranslating = true, onTranslateClick = {}, onTextChange = {})
    }
}

@KmmTranslatorPreview
@Composable
fun TranslateTextFieldPreviewTranslatedText() {
    KmmTranslatorTheme {
        TranslateTextField(isInIdleState = false, onTranslateClick = {}, onTextChange = {})
    }
}