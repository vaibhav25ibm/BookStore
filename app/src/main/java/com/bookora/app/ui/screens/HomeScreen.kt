package com.bookora.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.QrCodeScanner
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bookora.app.data.model.Book
import com.bookora.app.data.repository.BookRepository
import com.bookora.app.ui.components.*
import com.bookora.app.ui.theme.AppTheme

@Composable
fun HomeScreen(
    onBookClick: (Int) -> Unit = {},
    onCategoriesClick: () -> Unit = {},
    onCartClick: () -> Unit = {}
) {
    val colors = AppTheme.colors
    val typo   = AppTheme.typography

    var selectedCategory by remember { mutableStateOf("Fiction") }
    var searchQuery      by remember { mutableStateOf("") }
    var selectedNavIndex by remember { mutableIntStateOf(0) }

    Scaffold(
        containerColor = colors.background,
        topBar = {
            BookoraTopBar(
                title              = "Bookora",
                showBack           = false,
                showCart           = true,
                showNotification   = true,
                cartCount          = 3,
                onCartClick        = onCartClick
            )
        },
        bottomBar = {
            BookoraBottomNav(
                selectedIndex = selectedNavIndex,
                onItemSelected = { index ->
                    selectedNavIndex = index
                    when (index) {
                        1 -> onCategoriesClick()
                        3 -> onCartClick()
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            BookoraSearchBar(
                value         = searchQuery,
                onValueChange = { searchQuery = it },
                modifier      = Modifier.padding(horizontal = 16.dp),
                placeholder   = "Search books by title or author...",
                trailingIcon  = Icons.Outlined.QrCodeScanner
            )

            Spacer(modifier = Modifier.height(20.dp))

            SectionHeader(
                title    = "Categories",
                modifier = Modifier.padding(horizontal = 16.dp),
                onAction = onCategoriesClick
            )
            Spacer(modifier = Modifier.height(10.dp))
            CategoriesChipRow(
                categories = BookRepository.categories.map { it.name },
                selected   = selectedCategory,
                onSelect   = { selectedCategory = it }
            )

            Spacer(modifier = Modifier.height(24.dp))

            SectionHeader(
                title    = "Top Picks for You",
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            BooksRow(books = BookRepository.topPicks, onBookClick = onBookClick)

            Spacer(modifier = Modifier.height(24.dp))

            SectionHeader(
                title    = "Best Sellers",
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            BooksRow(books = BookRepository.bestSellers, onBookClick = onBookClick)

            Spacer(modifier = Modifier.height(24.dp))

            PromoBanner(modifier = Modifier.padding(horizontal = 16.dp))

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun CategoriesChipRow(
    categories: List<String>,
    selected: String,
    onSelect: (String) -> Unit
) {
    val colors = AppTheme.colors
    val typo   = AppTheme.typography

    Row(
        modifier = Modifier
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        categories.forEach { cat ->
            val isSelected = cat == selected
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(if (isSelected) colors.primary else colors.chipUnselected)
                    .clickable { onSelect(cat) }
                    .padding(horizontal = 18.dp, vertical = 8.dp)
            ) {
                Text(
                    text = cat,
                    style = typo.bodySmall,
                    fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                    color = if (isSelected) Color.White else colors.textPrimary
                )
            }
        }
    }
}

@Composable
private fun BooksRow(books: List<Book>, onBookClick: (Int) -> Unit) {
    Row(
        modifier = Modifier
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        books.forEach { book ->
            BookCard(book = book, onClick = { onBookClick(it.id) })
        }
    }
}

@Composable
private fun PromoBanner(modifier: Modifier = Modifier) {
    val colors = AppTheme.colors
    val typo   = AppTheme.typography

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(colors.promoBackground)
            .padding(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "Flat 20% OFF",       style = typo.titleLarge,  color = colors.primary)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "On Your First Order", style = typo.bodySmall,  color = colors.textPrimary)
                Text(text = "Use Code: ",          style = typo.bodySmall,  color = colors.textPrimary)
                Text(text = "WELCOME20",           style = typo.labelMedium, color = colors.accent)
                Spacer(modifier = Modifier.height(12.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(colors.primary)
                        .clickable {}
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(text = "Shop Now", style = typo.buttonSmall, color = Color.White)
                }
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text(text = "📚", style = typo.displayLarge)
            }
        }
    }
}
