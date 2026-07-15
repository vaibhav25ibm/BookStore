package com.bookora.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.bookora.app.data.repository.BookRepository
import com.bookora.app.ui.components.*
import com.bookora.app.ui.theme.AppTheme

data class CartItem(val book: Book, val quantity: Int = 1)

@Composable
fun CartScreen(
    onBack: () -> Unit = {},
    onWishlistClick: () -> Unit = {},
    onCheckoutClick: () -> Unit = {}
) {
    val colors = AppTheme.colors
    val typo   = AppTheme.typography

    var cartItems by remember {
        mutableStateOf(
            listOf(
                CartItem(BookRepository.topPicks[1], 1),
                CartItem(BookRepository.topPicks[0], 1),
                CartItem(BookRepository.topPicks[2], 1)
            )
        )
    }

    val subtotalOriginal = cartItems.sumOf { it.book.originalPrice * it.quantity }
    val subtotal         = cartItems.sumOf { it.book.price        * it.quantity }
    val discount         = subtotalOriginal - subtotal

    Scaffold(
        containerColor = colors.background,
        topBar = {
            BookoraTopBar(
                title    = "Cart",
                showBack = true,
                showCart = false,
                onBack   = onBack,
                actions  = {
                    IconButton(onClick = onWishlistClick) {
                        Icon(Icons.Outlined.FavoriteBorder, contentDescription = "Wishlist", tint = colors.textPrimary)
                    }
                    Box(contentAlignment = Alignment.TopEnd) {
                        IconButton(onClick = {}) {
                            Icon(Icons.Filled.ShoppingCart, contentDescription = null, tint = colors.primary)
                        }
                        Box(
                            modifier = Modifier
                                .offset(x = (-4).dp, y = 4.dp)
                                .size(16.dp)
                                .clip(androidx.compose.foundation.shape.CircleShape)
                                .background(colors.accent),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("${cartItems.size}", style = typo.caption, color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colors.background)
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                OrangeButton(
                    text     = "Proceed to Checkout",
                    onClick  = onCheckoutClick,
                    modifier = Modifier.fillMaxWidth(),
                    leadingContent = {
                        Icon(Icons.Filled.ArrowForward, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // Free delivery banner
            FreeDeliveryBanner()

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("${cartItems.size} Items in Cart", style = typo.titleSmall, color = colors.textPrimary)
                Text(
                    text = "Remove All",
                    style = typo.labelMedium,
                    color = colors.primary,
                    modifier = Modifier.clickable { cartItems = emptyList() }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            cartItems.forEachIndexed { index, cartItem ->
                CartItemRow(
                    cartItem  = cartItem,
                    onRemove  = { cartItems = cartItems.toMutableList().also { it.removeAt(index) } },
                    onIncrease = {
                        cartItems = cartItems.toMutableList().also {
                            it[index] = cartItem.copy(quantity = cartItem.quantity + 1)
                        }
                    },
                    onDecrease = {
                        if (cartItem.quantity > 1)
                            cartItems = cartItems.toMutableList().also {
                                it[index] = cartItem.copy(quantity = cartItem.quantity - 1)
                            }
                    }
                )
                if (index < cartItems.lastIndex)
                    HorizontalDivider(color = colors.border, modifier = Modifier.padding(horizontal = 16.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))

            PromoCodeRow()

            Spacer(modifier = Modifier.height(16.dp))

            OrderSummaryCard(
                subtotalLabel = "Subtotal (${cartItems.size} items)",
                subtotalValue = "₹$subtotalOriginal",
                discountValue = "-₹$discount",
                totalValue    = "₹$subtotal",
                modifier      = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun FreeDeliveryBanner() {
    val colors = AppTheme.colors
    val typo   = AppTheme.typography
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(12.dp))
            .border(1.dp, colors.border, RoundedCornerShape(12.dp))
            .background(colors.background)
            .clickable {}
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Icon(Icons.Filled.LocalShipping, contentDescription = null, tint = colors.textMuted, modifier = Modifier.size(22.dp))
            Text(text = "Yay! You are eligible for", style = typo.bodySmall, color = colors.textPrimary)
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("FREE Delivery", style = typo.labelMedium, color = colors.primary)
            Spacer(modifier = Modifier.width(6.dp))
            Icon(Icons.Filled.ChevronRight, contentDescription = null, tint = colors.textMuted, modifier = Modifier.size(18.dp))
        }
    }
}

@Composable
private fun CartItemRow(
    cartItem: CartItem,
    onRemove: () -> Unit,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit
) {
    val colors = AppTheme.colors
    val typo   = AppTheme.typography
    val book   = cartItem.book
    val discountPct = ((book.originalPrice - book.price).toFloat() / book.originalPrice * 100).toInt()

    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        AsyncImage(
            model = book.coverUrl,
            contentDescription = book.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(90.dp).height(120.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(colors.surface)
        )
        Column(modifier = Modifier.weight(1f)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Top) {
                Text(text = book.title, style = typo.bodyLarge, fontWeight = FontWeight.Bold, color = colors.textPrimary, modifier = Modifier.weight(1f))
                IconButton(onClick = onRemove, modifier = Modifier.size(32.dp)) {
                    Icon(Icons.Filled.Delete, contentDescription = "Remove", tint = colors.textMuted, modifier = Modifier.size(20.dp))
                }
            }
            Text(book.author,    style = typo.bodySmall, color = colors.textMuted)
            Text("Paperback",    style = typo.labelSmall, color = colors.textMuted)
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text("₹${book.price}",        style = typo.priceSmall, color = colors.textPrimary)
                    OriginalPrice("₹${book.originalPrice}")
                    DiscountBadge("$discountPct% OFF")
                }
                Text("₹${book.price * cartItem.quantity}", style = typo.titleSmall, color = colors.accent)
            }
            Spacer(modifier = Modifier.height(8.dp))
            InStockBadge()
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.align(Alignment.End), verticalAlignment = Alignment.CenterVertically) {
                QuantityButton(icon = Icons.Filled.Remove, onClick = onDecrease)
                Text("${cartItem.quantity}", style = typo.bodyLarge, fontWeight = FontWeight.Bold, color = colors.textPrimary, modifier = Modifier.padding(horizontal = 16.dp))
                QuantityButton(icon = Icons.Filled.Add, onClick = onIncrease)
            }
        }
    }
}

@Composable
private fun QuantityButton(icon: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit) {
    val colors = AppTheme.colors
    Box(
        modifier = Modifier
            .size(32.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, colors.border, RoundedCornerShape(8.dp))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = colors.textPrimary, modifier = Modifier.size(16.dp))
    }
}

@Composable
private fun PromoCodeRow() {
    val colors = AppTheme.colors
    val typo   = AppTheme.typography
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(12.dp))
            .border(1.dp, colors.border, RoundedCornerShape(12.dp))
            .background(colors.background)
            .clickable {}
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Icon(Icons.Filled.LocalOffer, contentDescription = null, tint = colors.textMuted, modifier = Modifier.size(20.dp))
            Text("Apply Promo Code", style = typo.bodyMedium, color = colors.textPrimary)
        }
        Icon(Icons.Filled.ChevronRight, contentDescription = null, tint = colors.textMuted)
    }
}
