package com.plcoding.translator_kmm.android.featuretranslate.presentation.compose

import android.speech.tts.TextToSpeech
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.plcoding.translator_kmm.android.KmmTranslatorTheme
import com.plcoding.translator_kmm.android.R
import com.plcoding.translator_kmm.android.core.compose.KmmTranslatorPreview
import com.plcoding.translator_kmm.android.core.compose.util.copyToClipboard
import com.plcoding.translator_kmm.android.core.compose.util.gradientSurface
import com.plcoding.translator_kmm.core.presentation.model.UiLanguage
import java.util.Locale

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TranslateTextField(
    modifier: Modifier = Modifier,
    fromText: String = "",
    toText: String? = null,
    fromLanguage: UiLanguage,
    toLanguage: UiLanguage,
    isInIdleState: Boolean = true,
    isTranslating: Boolean = false,
    onTranslateClick: () -> Unit,
    onTextChange: (String) -> Unit,
    onCloseTranslation: () -> Unit
) {
    val context = LocalContext.current
    val textToSpeech = rememberTextToSpeech()
    AnimatedContent(targetState = isInIdleState) { idleState ->
        if (idleState) {
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
                    value = fromText,
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
                                modifier = Modifier.size(24.dp),
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
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(20.dp))
                    .gradientSurface()
                    .padding(12.dp)
            ) {
                SmallLanguageIcon(
                    drawableRes = fromLanguage.drawableRes,
                    text = fromLanguage.language.langName
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = fromText,
                    minLines = 3,
                    color = MaterialTheme.colors.onBackground
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(bottom = 12.dp)
                ) {
                    IconButton(onClick = { copyToClipboard(context, fromText) }) {
                        Icon(
                            painter = painterResource(id = R.drawable.copy),
                            contentDescription = "Copy from text",
                            tint = Color.Unspecified
                        )
                    }

                    Spacer(modifier = Modifier.width(32.dp))

                    IconButton(onClick = { onCloseTranslation() }) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Close translation",
                            tint = Color(0xFFA8A5BB)
                        )
                    }
                }

                Divider(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colors.onBackground.copy(alpha = 0.3f)
                )

                Spacer(modifier = Modifier.height(8.dp))

                SmallLanguageIcon(
                    drawableRes = toLanguage.drawableRes,
                    text = toLanguage.language.langName
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = toText.toString(),
                    minLines = 3,
                    color = MaterialTheme.colors.onBackground
                )

                Row(modifier = Modifier.align(Alignment.End)) {
                    IconButton(
                        onClick = { copyToClipboard(context, toText.toString()) }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.copy),
                            contentDescription = "Copy from text",
                            tint = Color.Unspecified,
                        )
                    }

                    Spacer(modifier = Modifier.width(32.dp))

                    IconButton(onClick = {
                        textToSpeech.language = toLanguage.toLocale() ?: Locale.ENGLISH
                        textToSpeech.speak(
                            toText,
                            TextToSpeech.QUEUE_FLUSH,
                            null,
                            null
                        )
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.speaker),
                            contentDescription = "Copy from text",
                            tint = Color.Unspecified
                        )
                    }
                }
            }
        }
    }
}

@KmmTranslatorPreview
@Composable
fun TranslateTextFieldPreviewIdleState() {
    KmmTranslatorTheme {
        TranslateTextField(
            fromText = "This is a sample text",
            fromLanguage = UiLanguage.allLanguages[0],
            toLanguage = UiLanguage.allLanguages[1],
            onTranslateClick = {},
            onTextChange = {},
            onCloseTranslation = {}
        )
    }
}

@KmmTranslatorPreview
@Composable
fun TranslateTextFieldPreviewWithLoadingButton() {
    KmmTranslatorTheme {
        TranslateTextField(
            fromLanguage = UiLanguage.allLanguages[0],
            toLanguage = UiLanguage.allLanguages[1],
            isTranslating = true,
            onTranslateClick = {},
            onTextChange = {},
            onCloseTranslation = {}
        )
    }
}

@KmmTranslatorPreview
@Composable
fun TranslateTextFieldPreviewTranslatedText() {
    KmmTranslatorTheme {
        TranslateTextField(
            fromText = "Something",
            toText = "Something",
            fromLanguage = UiLanguage.allLanguages[0],
            toLanguage = UiLanguage.allLanguages[1],
            isInIdleState = false,
            onTranslateClick = {},
            onTextChange = {},
            onCloseTranslation = {}
        )
    }
}