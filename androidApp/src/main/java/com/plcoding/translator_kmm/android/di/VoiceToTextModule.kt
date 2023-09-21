package com.plcoding.translator_kmm.android.di

import android.app.Application
import com.plcoding.translator_kmm.android.featurevoicetotext.data.VoiceToTextParser
import com.plcoding.translator_kmm.featurevoicetotext.domain.IVoiceToTextParser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object VoiceToTextModule {
    @Provides
    @ViewModelScoped
    fun provideVoiceToTextParser(app: Application): IVoiceToTextParser {
        return VoiceToTextParser(app)
    }
}