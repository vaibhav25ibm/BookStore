package com.bookora.app.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// ─────────────────────────────────────────────────────────────────────────────
// AppColors — single source of truth for every colour used in the app.
// To retheme, change the defaults here or supply a custom AppColors instance
// to BookoraTheme.
// ─────────────────────────────────────────────────────────────────────────────
data class AppColors(
    // Brand
    val primary: Color        = BookoraPrimary,
    val secondary: Color      = BookoraSecondary,
    val accent: Color         = BookoraOrange,

    // Backgrounds & surfaces
    val background: Color     = BookoraBackground,
    val surface: Color        = BookoraSurface,
    val border: Color         = BookoraBorder,
    val promoBackground: Color= BookoraPromoBackground,
    val chipUnselected: Color = BookoraChipUnselected,

    // Text
    val textPrimary: Color    = BookoraText,
    val textMuted: Color      = BookoraMuted,
    val textOnPrimary: Color  = Color.White,

    // Status
    val success: Color        = BookoraSuccessGreen,
    val successBg: Color      = BookoraSuccessGreenBg,
    val error: Color          = Color(0xFFDC2626),
    val discountBg: Color     = Color(0xFFE8F5E9),
    val discountText: Color   = Color(0xFF2E7D32)
)

// ─────────────────────────────────────────────────────────────────────────────
// AppTypography — single source of truth for every text style.
// Change fontFamily here to switch the whole app font instantly.
// ─────────────────────────────────────────────────────────────────────────────
data class AppTypography(
    val fontFamily: FontFamily = FontFamily.Default,

    val displayLarge: TextStyle  = TextStyle(fontFamily = FontFamily.Default, fontWeight = FontWeight.ExtraBold, fontSize = 38.sp),
    val titleXL: TextStyle       = TextStyle(fontFamily = FontFamily.Default, fontWeight = FontWeight.ExtraBold, fontSize = 28.sp),
    val titleLarge: TextStyle    = TextStyle(fontFamily = FontFamily.Default, fontWeight = FontWeight.ExtraBold, fontSize = 22.sp),
    val titleMedium: TextStyle   = TextStyle(fontFamily = FontFamily.Default, fontWeight = FontWeight.Bold,      fontSize = 20.sp),
    val titleSmall: TextStyle    = TextStyle(fontFamily = FontFamily.Default, fontWeight = FontWeight.Bold,      fontSize = 17.sp),
    val sectionTitle: TextStyle  = TextStyle(fontFamily = FontFamily.Default, fontWeight = FontWeight.Bold,      fontSize = 16.sp),
    val bodyLarge: TextStyle     = TextStyle(fontFamily = FontFamily.Default, fontWeight = FontWeight.Normal,    fontSize = 15.sp),
    val bodyMedium: TextStyle    = TextStyle(fontFamily = FontFamily.Default, fontWeight = FontWeight.Normal,    fontSize = 14.sp),
    val bodySmall: TextStyle     = TextStyle(fontFamily = FontFamily.Default, fontWeight = FontWeight.Normal,    fontSize = 13.sp),
    val labelMedium: TextStyle   = TextStyle(fontFamily = FontFamily.Default, fontWeight = FontWeight.SemiBold,  fontSize = 13.sp),
    val labelSmall: TextStyle    = TextStyle(fontFamily = FontFamily.Default, fontWeight = FontWeight.Medium,    fontSize = 12.sp),
    val caption: TextStyle       = TextStyle(fontFamily = FontFamily.Default, fontWeight = FontWeight.Normal,    fontSize = 11.sp),
    val price: TextStyle         = TextStyle(fontFamily = FontFamily.Default, fontWeight = FontWeight.ExtraBold, fontSize = 24.sp),
    val priceSmall: TextStyle    = TextStyle(fontFamily = FontFamily.Default, fontWeight = FontWeight.Bold,      fontSize = 14.sp),
    val button: TextStyle        = TextStyle(fontFamily = FontFamily.Default, fontWeight = FontWeight.Bold,      fontSize = 16.sp),
    val buttonSmall: TextStyle   = TextStyle(fontFamily = FontFamily.Default, fontWeight = FontWeight.SemiBold,  fontSize = 13.sp)
)

// ─────────────────────────────────────────────────────────────────────────────
// CompositionLocals — access anywhere via LocalAppColors.current
// ─────────────────────────────────────────────────────────────────────────────
val LocalAppColors     = staticCompositionLocalOf { AppColors() }
val LocalAppTypography = staticCompositionLocalOf { AppTypography() }

// Convenience accessors
object AppTheme {
    val colors: AppColors
        @Composable
        get() = LocalAppColors.current
    val typography: AppTypography
        @Composable
        get() = LocalAppTypography.current
}
