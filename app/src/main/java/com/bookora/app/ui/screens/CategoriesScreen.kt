package com.bookora.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bookora.app.ui.components.*
import com.bookora.app.ui.theme.AppTheme

data class CategoryItem(
    val name: String,
    val bookCount: String,
    val icon: ImageVector,
    val bgColor: Color,
    val iconColor: Color
)

val categoryItems = listOf(
    CategoryItem("Fiction",           "1200+ Books", Icons.Filled.MenuBook,      Color(0xFFEEF0FF), Color(0xFF3D47E0)),
    CategoryItem("Non-Fiction",       "950+ Books",  Icons.Filled.Article,        Color(0xFFFFF3EC), Color(0xFFE87722)),
    CategoryItem("Self-Help",         "750+ Books",  Icons.Filled.Shield,         Color(0xFFF3EEFF), Color(0xFF7C3AED)),
    CategoryItem("Business",          "650+ Books",  Icons.Filled.Inventory2,     Color(0xFFEAF7F0), Color(0xFF16A34A)),
    CategoryItem("Biography",         "500+ Books",  Icons.Filled.Person,         Color(0xFFFFEEEE), Color(0xFFDC2626)),
    CategoryItem("Children",          "700+ Books",  Icons.Filled.School,         Color(0xFFE8F6FF), Color(0xFF0284C7)),
    CategoryItem("Science",           "400+ Books",  Icons.Filled.Science,        Color(0xFFEEF4FF), Color(0xFF2563EB)),
    CategoryItem("History",           "350+ Books",  Icons.Filled.AccountBalance, Color(0xFFFFFBEB), Color(0xFFD97706)),
    CategoryItem("Art & Photography", "450+ Books",  Icons.Filled.Palette,        Color(0xFFFFF0F8), Color(0xFFDB2777)),
    CategoryItem("Literature",        "800+ Books",  Icons.Filled.Bookmark,       Color(0xFFE8FFFE), Color(0xFF0D9488)),
    CategoryItem("Romance",           "600+ Books",  Icons.Filled.Favorite,       Color(0xFFF5F0FF), Color(0xFF7C3AED)),
    CategoryItem("Mystery & Thriller","900+ Books",  Icons.Filled.Search,         Color(0xFFEFF6FF), Color(0xFF1D4ED8)),
    CategoryItem("Fantasy",           "550+ Books",  Icons.Filled.RocketLaunch,   Color(0xFFEFFEF0), Color(0xFF16A34A)),
    CategoryItem("Young Adult",       "650+ Books",  Icons.Filled.EmojiEmotions,  Color(0xFFFFEEEE), Color(0xFFDC2626))
)

@Composable
fun CategoriesScreen(
    onBack: () -> Unit = {},
    onCartClick: () -> Unit = {}
) {
    val colors = AppTheme.colors
    var searchQuery by remember { mutableStateOf("") }

    val filtered = remember(searchQuery) {
        if (searchQuery.isBlank()) categoryItems
        else categoryItems.filter { it.name.contains(searchQuery, ignoreCase = true) }
    }

    Scaffold(
        containerColor = colors.background,
        topBar = {
            BookoraTopBar(
                title       = "Categories",
                showBack    = true,
                showCart    = true,
                cartCount   = 3,
                onBack      = onBack,
                onCartClick = onCartClick
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            BookoraSearchBar(
                value         = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder   = "Search categories..."
            )

            Spacer(modifier = Modifier.height(20.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(filtered) { category ->
                    CategoryCard(category = category)
                }
            }
        }
    }
}

@Composable
private fun CategoryCard(category: CategoryItem) {
    val colors = AppTheme.colors
    val typo   = AppTheme.typography

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, colors.border, RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .background(colors.background)
            .clickable {}
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(category.bgColor),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = category.icon,
                contentDescription = category.name,
                tint = category.iconColor,
                modifier = Modifier.size(26.dp)
            )
        }
        Column {
            Text(text = category.name,      style = typo.labelMedium, color = colors.textPrimary, fontWeight = FontWeight.Bold)
            Text(text = category.bookCount, style = typo.caption,     color = colors.textMuted)
        }
    }
}
