package com.bookora.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.bookora.app.ui.theme.AppTheme

/**
 * Custom radio button that follows app theme colours.
 *
 * @param selected   Whether this option is selected.
 * @param onSelect   Selection callback.
 */
@Composable
fun RadioSelector(
    selected: Boolean,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = AppTheme.colors

    Box(
        modifier = modifier
            .size(20.dp)
            .border(2.dp, if (selected) colors.primary else colors.textMuted, CircleShape)
            .clip(CircleShape)
            .clickable { onSelect() },
        contentAlignment = Alignment.Center
    ) {
        if (selected) {
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .clip(CircleShape)
                    .background(colors.primary)
            )
        }
    }
}
