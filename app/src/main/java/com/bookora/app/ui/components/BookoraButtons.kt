package com.bookora.app.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bookora.app.ui.theme.AppTheme

private val defaultShape   = RoundedCornerShape(12.dp)
private val defaultHeight  = 54.dp

/** Blue filled primary button. */
@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    height: Dp = defaultHeight,
    enabled: Boolean = true,
    leadingContent: @Composable RowScope.() -> Unit = {}
) {
    val colors = AppTheme.colors
    val typo   = AppTheme.typography
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier.height(height),
        colors = ButtonDefaults.buttonColors(containerColor = colors.primary),
        shape = defaultShape
    ) {
        leadingContent()
        Text(text = text, style = typo.button, color = Color.White)
    }
}

/** Orange accent CTA button (cart → checkout → pay flow). */
@Composable
fun OrangeButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    height: Dp = 56.dp,
    leadingContent: @Composable RowScope.() -> Unit = {}
) {
    val colors = AppTheme.colors
    val typo   = AppTheme.typography
    Button(
        onClick = onClick,
        modifier = modifier.height(height),
        colors = ButtonDefaults.buttonColors(containerColor = colors.accent),
        shape = RoundedCornerShape(14.dp)
    ) {
        leadingContent()
        Text(text = text, style = typo.button, color = Color.White)
    }
}

/** Outlined (ghost) button with primary-coloured border + text. */
@Composable
fun OutlineButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    height: Dp = defaultHeight,
    leadingContent: @Composable RowScope.() -> Unit = {}
) {
    val colors = AppTheme.colors
    val typo   = AppTheme.typography
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.height(height),
        border = ButtonDefaults.outlinedButtonBorder.copy(brush = SolidColor(colors.primary)),
        shape = defaultShape
    ) {
        leadingContent()
        Text(text = text, style = typo.button, color = colors.primary)
    }
}
