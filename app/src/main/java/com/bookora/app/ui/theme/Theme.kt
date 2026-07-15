package com.bookora.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

private val LightColorScheme = lightColorScheme(
    primary       = BookoraPrimary,
    secondary     = BookoraSecondary,
    background    = BookoraBackground,
    surface       = BookoraSurface,
    onPrimary     = BookoraBackground,
    onBackground  = BookoraText,
    onSurface     = BookoraText
)

/**
 * Main app theme.
 * To change colours app-wide: pass a custom [appColors].
 * To change fonts app-wide:   pass a custom [appTypography].
 *
 * Example — dark-ish purple retheme:
 * ```
 * BookoraTheme(
 *   appColors = AppColors(primary = Color(0xFF6200EE), accent = Color(0xFFFF6D00))
 * ) { ... }
 * ```
 */
@Composable
fun BookoraTheme(
    appColors: AppColors         = AppColors(),
    appTypography: AppTypography = AppTypography(),
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalAppColors     provides appColors,
        LocalAppTypography provides appTypography
    ) {
        MaterialTheme(
            colorScheme = LightColorScheme.copy(
                primary    = appColors.primary,
                secondary  = appColors.secondary,
                background = appColors.background,
                surface    = appColors.surface,
                onPrimary  = appColors.textOnPrimary,
                onBackground = appColors.textPrimary,
                onSurface  = appColors.textPrimary
            ),
            content = content
        )
    }
}
