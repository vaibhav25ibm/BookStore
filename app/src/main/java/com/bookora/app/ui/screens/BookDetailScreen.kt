package com.bookora.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.bookora.app.data.model.Book
import com.bookora.app.ui.components.*
import com.bookora.app.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailScreen(book: Book, onBack: () -> Unit) {
    val colors = AppTheme.colors
    val typo   = AppTheme.typography

    val discount = ((book.originalPrice - book.price).toFloat() / book.originalPrice * 100).toInt()

    Scaffold(
        containerColor = colors.background,
        topBar = {
            BookoraTopBar(
                title    = book.title,
                showBack = true,
                showCart = false,
                onBack   = onBack,
                actions  = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Filled.FavoriteBorder, contentDescription = "Wishlist", tint = colors.textPrimary)
                    }
                    IconButton(onClick = {}) {
                        Icon(Icons.Filled.Share, contentDescription = "Share", tint = colors.textPrimary)
                    }
                }
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colors.background)
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlineButton(
                    text     = "Add to Cart",
                    onClick  = {},
                    modifier = Modifier.weight(1f).height(50.dp),
                    leadingContent = {
                        Icon(Icons.Filled.ShoppingCart, contentDescription = null, tint = colors.primary, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                    }
                )
                PrimaryButton(
                    text     = "Buy Now",
                    onClick  = {},
                    modifier = Modifier.weight(1f),
                    height   = 50.dp
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(colors.surface),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = book.coverUrl,
                    contentDescription = book.title,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.height(260.dp).clip(RoundedCornerShape(12.dp))
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(text = book.title,        style = typo.titleLarge,  color = colors.textPrimary)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "by ${book.author}", style = typo.bodyLarge, color = colors.textMuted)

            Spacer(modifier = Modifier.height(12.dp))

            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(colors.accent.copy(alpha = 0.12f))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text("${book.rating} ★", style = typo.labelMedium, color = colors.accent, fontWeight = FontWeight.Bold)
                }
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(colors.primary.copy(alpha = 0.1f))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(book.category, style = typo.labelMedium, color = colors.primary)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("₹${book.price}",        style = typo.price,     color = colors.primary)
                OriginalPrice("₹${book.originalPrice}")
                DiscountBadge("$discount% OFF")
            }

            Spacer(modifier = Modifier.height(20.dp))
            HorizontalDivider(color = colors.border)
            Spacer(modifier = Modifier.height(16.dp))

            Text("About this book", style = typo.sectionTitle, color = colors.textPrimary)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = book.description.ifEmpty {
                    "This is a highly acclaimed book loved by millions of readers worldwide. " +
                    "It offers unique perspectives and life-changing insights that will transform " +
                    "the way you think and live. A must-read for every avid reader."
                },
                style = typo.bodyMedium,
                color = colors.textMuted,
                lineHeight = androidx.compose.ui.unit.TextUnit.Unspecified
            )

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
