package com.example.translator_kmm.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.Surface
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.translator_kmm.android.core.compose.navigation.Routes
import com.example.translator_kmm.android.featuretranslate.presentation.AndroidTranslateViewModel
import com.example.translator_kmm.android.featuretranslate.presentation.TranslateScreen
import com.example.translator_kmm.android.featurevoicetotext.presentation.AndroidVoiceToTextViewModel
import com.example.translator_kmm.android.featurevoicetotext.presentation.VoiceToTextScreen
import com.example.translator_kmm.android.theme.darkColors
import com.example.translator_kmm.android.theme.lightColors
import com.example.translator_kmm.featuretranslate.presentation.TranslateEvent
import dagger.hilt.android.AndroidEntryPoint

@Composable
fun KmmTranslatorTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        darkColors
    } else {
        lightColors
    }
    val SfProText = FontFamily(
        Font(
            resId = R.font.sf_pro_text_regular,
            weight = FontWeight.Normal
        ),
        Font(
            resId = R.font.sf_pro_text_medium,
            weight = FontWeight.Medium
        ),
        Font(
            resId = R.font.sf_pro_text_bold,
            weight = FontWeight.Bold
        ),
    )
    val typography = Typography(
        h1 = TextStyle(
            fontFamily = SfProText,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp
        ),
        h2 = TextStyle(
            fontFamily = SfProText,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        ),
        h3 = TextStyle(
            fontFamily = SfProText,
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp
        ),
        body1 = TextStyle(
            fontFamily = SfProText,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp
        ),
        body2 = TextStyle(
            fontFamily = SfProText,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp
        ),
    )
    val shapes = Shapes(
        small = RoundedCornerShape(4.dp),
        medium = RoundedCornerShape(4.dp),
        large = RoundedCornerShape(0.dp)
    )

    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KmmTranslatorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    TranslateRoot()
                }
            }
        }
    }
}

@Composable
fun TranslateRoot() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Routes.TRANSLATE
    ) {
        composable(route = Routes.TRANSLATE) {
            val viewModel = hiltViewModel<AndroidTranslateViewModel>()
            val state by viewModel.state.collectAsState()

            val voiceResult by it
                .savedStateHandle
                .getStateFlow<String?>("voiceResult", null)
                .collectAsState()

            LaunchedEffect(voiceResult) {
                if (voiceResult != null) {
                    viewModel.onEvent(TranslateEvent.SubmitVoiceResult(voiceResult))
                    it.savedStateHandle["voiceResult"] = null
                }
            }

            TranslateScreen(
                state = state,
                onEvent = { event ->
                    when (event) {
                        is TranslateEvent.RecordAudio -> {
                            navController.navigate(
                                Routes.VOICE_RECORD + "/{${state.fromLanguage.language.langCode}}"
                            )
                        }

                        else -> viewModel.onEvent(event)
                    }
                }
            )
        }
        composable(
            route = Routes.VOICE_RECORD + "/{languageCode}",
            arguments = listOf(
                navArgument("languageCode") {
                    type = NavType.StringType
                    defaultValue = "en"
                }
            )
        ) { backStackEntry ->
            val languageCode = backStackEntry.arguments?.getString("languageCode") ?: "en"
            val viewModel = hiltViewModel<AndroidVoiceToTextViewModel>()
            val state by viewModel.state.collectAsState()
            VoiceToTextScreen(
                state = state,
                languageCode = languageCode,
                onResult = { result ->
                    navController.previousBackStackEntry?.savedStateHandle?.set(
                        "voiceResult", result
                    )
                    navController.popBackStack()
                },
                onEvent = { event ->
                    viewModel.onEvent(event)
                }
            )
        }
    }
}