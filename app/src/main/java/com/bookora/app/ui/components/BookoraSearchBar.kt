package com.bookora.app.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.bookora.app.ui.theme.AppTheme

/**
 * Reusable search bar used on Home and Categories screens.
 *
 * @param value         Current text value.
 * @param onValueChange Text change callback.
 * @param placeholder   Hint text shown when empty.
 * @param trailingIcon  Optional trailing icon.
 */
@Composable
fun BookoraSearchBar(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Search...",
    trailingIcon: ImageVector? = null
) {
    val colors = AppTheme.colors

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        placeholder = {
            Text(placeholder, color = colors.textMuted, fontSize = AppTheme.typography.bodyMedium.fontSize)
        },
        leadingIcon = {
            Icon(imageVector = Icons.Filled.Search, contentDescription = null, tint = colors.textMuted)
        },
        trailingIcon = trailingIcon?.let {
            { Icon(imageVector = it, contentDescription = null, tint = colors.textMuted) }
        },
        singleLine = true,
        shape = RoundedCornerShape(28.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor   = colors.border,
            focusedBorderColor     = colors.primary,
            unfocusedContainerColor = colors.surface,
            focusedContainerColor  = colors.background
        )
    )
}
