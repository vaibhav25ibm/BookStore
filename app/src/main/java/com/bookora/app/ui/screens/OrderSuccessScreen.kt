package com.bookora.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.bookora.app.data.repository.BookRepository
import com.bookora.app.ui.components.*
import com.bookora.app.ui.theme.AppTheme

private val ConfettiColors = listOf(
    Color(0xFF22A94B), Color(0xFFE87722), Color(0xFF3D47E0),
    Color(0xFFFFCC00), Color(0xFF7C3AED), Color(0xFFDC2626)
)

@Composable
fun OrderSuccessScreen(
    onContinueShopping: () -> Unit = {},
    onViewOrders: () -> Unit = {}
) {
    val colors    = AppTheme.colors
    val typo      = AppTheme.typography
    val cartBooks = BookRepository.topPicks.take(3)
    val totalPaid = cartBooks.sumOf { it.price }

    Scaffold(
        containerColor = colors.successBg,
        bottomBar = {
            BookoraBottomNav()
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(36.dp))

            SuccessHeroSection(successColor = colors.success, successBg = colors.successBg)

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Order Placed Successfully!",
                style = typo.titleLarge,
                color = colors.success,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 24.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Thank you for shopping with us.\nYour order has been placed and is confirmed.",
                style = typo.bodyMedium,
                color = colors.textMuted,
                textAlign = TextAlign.Center,
                lineHeight = androidx.compose.ui.unit.TextUnit.Unspecified,
                modifier = Modifier.padding(horizontal = 32.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            OrderInfoCard(totalPaid = totalPaid, modifier = Modifier.padding(horizontal = 16.dp))

            Spacer(modifier = Modifier.height(24.dp))

            Column(modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth()) {
                Text(text = "What happens next?", style = typo.sectionTitle, color = colors.textPrimary)
                Spacer(modifier = Modifier.height(16.dp))
                OrderTrackingRow(successColor = colors.success, successBg = colors.successBg)
            }

            Spacer(modifier = Modifier.height(24.dp))

            OrderDetailsCard(
                books     = cartBooks.map { it.coverUrl },
                itemCount = cartBooks.size,
                modifier  = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            EmailBanner(modifier = Modifier.padding(horizontal = 16.dp))

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlineButton(
                    text     = "Continue\nShopping",
                    onClick  = onContinueShopping,
                    modifier = Modifier.weight(1f).height(52.dp),
                    leadingContent = {
                        Icon(Icons.Filled.ShoppingBag, contentDescription = null, tint = colors.primary, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                    }
                )
                PrimaryButton(
                    text     = "View My\nOrders",
                    onClick  = onViewOrders,
                    modifier = Modifier.weight(1f),
                    height   = 52.dp,
                    leadingContent = {
                        Icon(Icons.Filled.Receipt, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                    }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Hero: confetti + green checkmark
// ─────────────────────────────────────────────────────────────────────────────
@Composable
private fun SuccessHeroSection(successColor: Color, successBg: Color) {
    val dots = listOf(
        Triple(0.12f, 0.08f, ConfettiColors[1]),
        Triple(0.28f, 0.02f, ConfettiColors[0]),
        Triple(0.72f, 0.04f, ConfettiColors[3]),
        Triple(0.85f, 0.12f, ConfettiColors[2]),
        Triple(0.92f, 0.38f, ConfettiColors[1]),
        Triple(0.82f, 0.65f, ConfettiColors[4]),
        Triple(0.60f, 0.82f, ConfettiColors[5]),
        Triple(0.38f, 0.88f, ConfettiColors[3]),
        Triple(0.10f, 0.72f, ConfettiColors[2]),
        Triple(0.04f, 0.42f, ConfettiColors[4]),
        Triple(0.18f, 0.50f, ConfettiColors[0]),
        Triple(0.76f, 0.50f, ConfettiColors[5])
    )
    Box(modifier = Modifier.fillMaxWidth().height(130.dp), contentAlignment = Alignment.Center) {
        dots.forEach { (xFrac, yFrac, color) ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.TopStart, unbounded = true)
                    .offset(x = (xFrac * 360).dp, y = (yFrac * 130).dp)
                    .size(7.dp)
                    .clip(CircleShape)
                    .background(color)
            )
        }
        Box(modifier = Modifier.size(96.dp).clip(CircleShape).background(successBg), contentAlignment = Alignment.Center) {
            Box(modifier = Modifier.size(76.dp).clip(CircleShape).background(successColor), contentAlignment = Alignment.Center) {
                Icon(Icons.Filled.Check, contentDescription = "Success", tint = Color.White, modifier = Modifier.size(44.dp))
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Order ID / Date / Amount card
// ─────────────────────────────────────────────────────────────────────────────
@Composable
private fun OrderInfoCard(totalPaid: Int, modifier: Modifier = Modifier) {
    val colors = AppTheme.colors
    val typo   = AppTheme.typography

    Column(
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, colors.border, RoundedCornerShape(14.dp))
            .clip(RoundedCornerShape(14.dp))
            .background(colors.background)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.size(52.dp).clip(RoundedCornerShape(10.dp)).background(colors.successBg), contentAlignment = Alignment.Center) {
                Icon(Icons.Filled.Receipt, contentDescription = null, tint = colors.success, modifier = Modifier.size(28.dp))
            }
            Column(modifier = Modifier.weight(1f)) {
                Text("Order ID", style = typo.caption, color = colors.textMuted)
                Spacer(modifier = Modifier.height(2.dp))
                Text("#ORD1234567890", style = typo.titleSmall, color = colors.textPrimary)
            }
            Column(horizontalAlignment = Alignment.End) {
                Text("Order Date", style = typo.caption, color = colors.textMuted)
                Spacer(modifier = Modifier.height(2.dp))
                Text("25 May 2024,", style = typo.labelMedium, color = colors.textPrimary)
                Text("09:30 AM",     style = typo.labelMedium, color = colors.textPrimary)
            }
        }

        HorizontalDivider(color = colors.border)

        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text("Total Amount Paid", style = typo.bodySmall, color = colors.textMuted)
                Spacer(modifier = Modifier.height(4.dp))
                Text("₹$totalPaid", style = typo.price, color = colors.textPrimary)
            }
            Row(
                modifier = Modifier
                    .border(1.5.dp, colors.success, RoundedCornerShape(20.dp))
                    .padding(horizontal = 14.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(Icons.Filled.CheckCircle, contentDescription = null, tint = colors.success, modifier = Modifier.size(16.dp))
                Text("Payment Successful", style = typo.labelMedium, color = colors.success)
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// 4-step order tracking row
// ─────────────────────────────────────────────────────────────────────────────
data class TrackStep(val icon: ImageVector, val label: String, val desc: String, val done: Boolean, val dashed: Boolean = false)

@Composable
private fun OrderTrackingRow(successColor: Color, successBg: Color) {
    val colors = AppTheme.colors
    val typo   = AppTheme.typography
    val steps  = listOf(
        TrackStep(Icons.Filled.Receipt,       "Order\nConfirmed", "We've received\nyour order.",   true),
        TrackStep(Icons.Filled.Inventory2,    "Processing",       "We are packing\nyour items.",   true),
        TrackStep(Icons.Filled.LocalShipping, "Shipped",          "Your order is on\nits way.",    true),
        TrackStep(Icons.Filled.Home,          "Delivered",        "Expected by\n29 May 2024",     false, dashed = true)
    )

    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top) {
        steps.forEachIndexed { index, step ->
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f)) {
                Box(
                    modifier = Modifier
                        .size(54.dp)
                        .clip(CircleShape)
                        .background(if (step.done) successBg else Color(0xFFF0F0F0)),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Icon(step.icon, contentDescription = step.label, tint = if (step.done) successColor else colors.textMuted, modifier = Modifier.size(26.dp))
                    }
                    if (step.done) {
                        Box(
                            modifier = Modifier
                                .size(18.dp)
                                .clip(CircleShape)
                                .background(successColor)
                                .border(1.5.dp, Color.White, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Filled.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(10.dp))
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(step.label, style = typo.caption, fontWeight = if (step.done) FontWeight.Bold else FontWeight.Normal, color = if (step.done) colors.textPrimary else colors.textMuted, textAlign = TextAlign.Center, lineHeight = androidx.compose.ui.unit.TextUnit.Unspecified)
                Spacer(modifier = Modifier.height(4.dp))
                Text(step.desc,  style = typo.caption, color = colors.textMuted, textAlign = TextAlign.Center, lineHeight = androidx.compose.ui.unit.TextUnit.Unspecified)
            }

            if (index < steps.lastIndex) {
                Box(
                    modifier = Modifier
                        .width(20.dp).height(2.dp)
                        .align(Alignment.Top).offset(y = 27.dp)
                        .background(if (!steps[index + 1].dashed) successColor else colors.border, RoundedCornerShape(1.dp))
                )
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Order details card
// ─────────────────────────────────────────────────────────────────────────────
@Composable
private fun OrderDetailsCard(books: List<String>, itemCount: Int, modifier: Modifier = Modifier) {
    val colors = AppTheme.colors
    val typo   = AppTheme.typography

    Column(
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, colors.border, RoundedCornerShape(14.dp))
            .clip(RoundedCornerShape(14.dp))
            .background(colors.background)
            .padding(16.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text("Order Details", style = typo.bodyLarge, fontWeight = FontWeight.Bold, color = colors.textPrimary)
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable {}) {
                Text("View Details", style = typo.labelMedium, color = colors.primary)
                Icon(Icons.Filled.ChevronRight, contentDescription = null, tint = colors.primary, modifier = Modifier.size(18.dp))
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.width((books.size * 56 + 8).dp).height(80.dp)) {
                books.take(3).forEachIndexed { index, url ->
                    AsyncImage(
                        model = url,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .offset(x = (index * 52).dp)
                            .size(width = 60.dp, height = 80.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .border(1.5.dp, Color.White, RoundedCornerShape(6.dp))
                            .background(colors.surface)
                    )
                }
            }
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text("$itemCount Items", style = typo.labelMedium, fontWeight = FontWeight.Bold, color = colors.textPrimary)
                Spacer(modifier = Modifier.height(4.dp))
                Text("Will be delivered to:",      style = typo.caption, color = colors.textMuted)
                Text("123, Green Park Apartment,\nMG Road, Bangalore,\nKarnataka - 560001", style = typo.bodySmall, color = colors.textPrimary, lineHeight = androidx.compose.ui.unit.TextUnit.Unspecified)
            }
            Icon(Icons.Filled.ChevronRight, contentDescription = null, tint = colors.textMuted, modifier = Modifier.size(18.dp).align(Alignment.CenterVertically))
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Email confirmation banner
// ─────────────────────────────────────────────────────────────────────────────
@Composable
private fun EmailBanner(modifier: Modifier = Modifier) {
    val colors = AppTheme.colors
    val typo   = AppTheme.typography

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(colors.successBg)
            .padding(horizontal = 14.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(Icons.Filled.Shield, contentDescription = null, tint = colors.success, modifier = Modifier.size(28.dp))
        Column {
            Text("You will receive an email confirmation shortly", style = typo.labelMedium, color = colors.textPrimary)
            Spacer(modifier = Modifier.height(2.dp))
            Text("We have also sent the order details to your registered email ID.", style = typo.caption, color = colors.textMuted, lineHeight = androidx.compose.ui.unit.TextUnit.Unspecified)
        }
    }
}
