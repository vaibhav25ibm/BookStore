package com.bookora.app.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.bookora.app.ui.theme.AppTheme

/**
 * Section title row with an optional "See all" / action link on the right.
 *
 * @param title      Section heading.
 * @param actionText Label for the trailing action link (null = hidden).
 * @param onAction   Action callback.
 */
@Composable
fun SectionHeader(
    title: String,
    modifier: Modifier = Modifier,
    actionText: String? = "See all",
    onAction: () -> Unit = {}
) {
    val colors = AppTheme.colors
    val typo   = AppTheme.typography

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = typo.titleSmall,
            color = colors.textPrimary
        )
        if (actionText != null) {
            Text(
                text = actionText,
                style = typo.labelMedium,
                color = colors.primary,
                modifier = Modifier.clickable { onAction() }
            )
        }
    }
}
