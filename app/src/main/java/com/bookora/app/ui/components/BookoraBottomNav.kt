package com.bookora.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bookora.app.ui.theme.AppTheme

data class BottomNavItem(
    val label: String,
    val filledIcon: ImageVector,
    val outlinedIcon: ImageVector,
    val badgeCount: Int = 0
)

val defaultBottomNavItems = listOf(
    BottomNavItem("Home",       Icons.Filled.Home,         Icons.Outlined.Home),
    BottomNavItem("Categories", Icons.Filled.GridView,     Icons.Outlined.GridView),
    BottomNavItem("Wishlist",   Icons.Filled.Favorite,     Icons.Outlined.FavoriteBorder),
    BottomNavItem("Cart",       Icons.Filled.ShoppingCart, Icons.Outlined.ShoppingCart, badgeCount = 3),
    BottomNavItem("Profile",    Icons.Filled.Person,       Icons.Outlined.Person)
)

/**
 * Reusable bottom navigation bar.
 *
 * @param items          List of [BottomNavItem] to render.
 * @param selectedIndex  Currently selected tab index.
 * @param onItemSelected Callback with the tapped index.
 */
@Composable
fun BookoraBottomNav(
    items: List<BottomNavItem> = defaultBottomNavItems,
    selectedIndex: Int = 0,
    onItemSelected: (Int) -> Unit = {}
) {
    val colors = AppTheme.colors
    val typo   = AppTheme.typography

    NavigationBar(
        containerColor = colors.background,
        tonalElevation = 0.dp,
        modifier = Modifier.background(colors.background)
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedIndex == index,
                onClick  = { onItemSelected(index) },
                icon = {
                    BadgedBox(
                        badge = {
                            if (item.badgeCount > 0) {
                                Badge(containerColor = colors.accent) {
                                    Text("${item.badgeCount}")
                                }
                            }
                        }
                    ) {
                        Icon(
                            imageVector = if (selectedIndex == index) item.filledIcon else item.outlinedIcon,
                            contentDescription = item.label
                        )
                    }
                },
                label = { Text(item.label, fontSize = typo.caption.fontSize) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor   = colors.primary,
                    selectedTextColor   = colors.primary,
                    unselectedIconColor = colors.textMuted,
                    unselectedTextColor = colors.textMuted,
                    indicatorColor      = Color.Transparent
                )
            )
        }
    }
}
