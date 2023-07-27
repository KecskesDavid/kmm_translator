package com.plcoding.translator_kmm.android.core.compose.util

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// compose used because the modifier is stateful, it changes when we change the theme
fun Modifier.gradientSurface(): Modifier = composed {
    if(isSystemInDarkTheme()) {
        Modifier.background(
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color(0xFF23262E),
                    Color(0xFF212329)
                )
            )
        )
    } else Modifier.background(MaterialTheme.colors.surface)
}