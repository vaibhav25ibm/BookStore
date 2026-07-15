package com.bookora.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bookora.app.ui.theme.AppTheme

/**
 * Reusable top app bar for all screens.
 *
 * @param title          Screen / brand title text.
 * @param showBack       Show back arrow instead of hamburger menu.
 * @param showCart       Show cart icon with optional badge count.
 * @param showNotification Show notification bell.
 * @param cartCount      Badge count on cart icon (0 = no badge).
 * @param onBack         Back-press callback.
 * @param onMenuClick    Hamburger menu callback.
 * @param onCartClick    Cart icon callback.
 * @param actions        Optional extra composable content on the right.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookoraTopBar(
    title: String,
    showBack: Boolean = false,
    showCart: Boolean = true,
    showNotification: Boolean = false,
    cartCount: Int = 0,
    onBack: () -> Unit = {},
    onMenuClick: () -> Unit = {},
    onCartClick: () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {}
) {
    val colors = AppTheme.colors
    val typo   = AppTheme.typography

    TopAppBar(
        navigationIcon = {
            if (showBack) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = colors.textPrimary
                    )
                }
            } else {
                IconButton(onClick = onMenuClick) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = "Menu",
                        tint = colors.textPrimary
                    )
                }
            }
        },
        title = {
            Text(
                text = title,
                fontSize = typo.titleMedium.fontSize,
                fontWeight = FontWeight.ExtraBold,
                color = if (!showBack) colors.primary else colors.textPrimary
            )
        },
        actions = {
            // Extra caller-supplied actions
            actions()

            if (showNotification) {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Outlined.Notifications,
                        contentDescription = "Notifications",
                        tint = colors.textPrimary
                    )
                }
            }

            if (showCart) {
                Box(contentAlignment = Alignment.TopEnd) {
                    IconButton(onClick = onCartClick) {
                        Icon(
                            imageVector = Icons.Outlined.ShoppingCart,
                            contentDescription = "Cart",
                            tint = if (cartCount > 0) colors.primary else colors.textPrimary
                        )
                    }
                    if (cartCount > 0) {
                        Box(
                            modifier = Modifier
                                .offset(x = (-4).dp, y = 4.dp)
                                .size(16.dp)
                                .clip(CircleShape)
                                .background(colors.accent),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "$cartCount",
                                fontSize = 9.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = colors.background)
    )
}
