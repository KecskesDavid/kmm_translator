package com.example.kmm_translator.featurevoicetotext.presentation

import android.Manifest
import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import androidx.test.rule.GrantPermissionRule
import com.example.kmm_translator.featuretranslate.data.FakeVoiceToTextParser
import com.example.kmm_translator.featurevoicetotext.data.remote.FakeTranslateClient
import com.example.translator_kmm.android.MainActivity
import com.example.translator_kmm.android.R
import com.example.translator_kmm.android.di.KmmTranslateModule
import com.example.translator_kmm.android.di.VoiceToTextModule
import com.example.translator_kmm.featuretranslate.domain.translate.ITranslateClient
import com.example.translator_kmm.featurevoicetotext.domain.IVoiceToTextParser
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
@UninstallModules(KmmTranslateModule::class, VoiceToTextModule::class)
class VoiceToTextEndToEnd {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val permissionRule = GrantPermissionRule.grant(
        Manifest.permission.RECORD_AUDIO
    )

    @Inject
    lateinit var fakeVoiceParser: IVoiceToTextParser

    @Inject
    lateinit var fakeClient: ITranslateClient


    @Before
    fun setup() {
        // This injects all the dependencies
        hiltRule.inject()
    }

    @Test
    fun recordAndTranslate() = runBlocking<Unit> {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val parser = fakeVoiceParser as FakeVoiceToTextParser
        val client = fakeClient as FakeTranslateClient

        composeRule
            .onNodeWithContentDescription(context.getString(R.string.record_audio))
            .performClick()

        // To record
        composeRule
            .onNodeWithContentDescription(context.getString(R.string.record_audio))
            .performClick()

        // To stop recording
        composeRule
            .onNodeWithContentDescription(context.getString(R.string.stop_recording))
            .performClick()

        composeRule
            .onNodeWithText(parser.voiceResult)
            .assertIsDisplayed()

        composeRule
            .onNodeWithContentDescription(context.getString(R.string.apply))
            .performClick()

        composeRule
            .onNodeWithText(parser.voiceResult)
            .assertIsDisplayed()

        composeRule
            .onNodeWithText(context.getString(R.string.translate), ignoreCase = true)
            .performClick()

        composeRule
            .onNodeWithText(parser.voiceResult)
            .assertIsDisplayed()

        composeRule
            .onNodeWithText(FakeTranslateClient.TRANSLATED_TEXT)
            .assertIsDisplayed()

    }
}