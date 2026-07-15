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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.bookora.app.data.repository.BookRepository
import com.bookora.app.ui.components.*
import com.bookora.app.ui.theme.AppTheme

data class DeliveryAddress(
    val label: String,
    val isDefault: Boolean,
    val name: String,
    val address: String,
    val phone: String
)

private val addresses = listOf(
    DeliveryAddress("Home",   true,  "John Doe", "123, Green Park Apartment, MG Road,\nBangalore, Karnataka - 560001\nIndia", "98765 43210"),
    DeliveryAddress("Office", false, "John Doe", "45, Prestige Tech Park, Marathahalli,\nBangalore, Karnataka - 560037\nIndia", "91234 56789")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
    onBack: () -> Unit = {},
    onProceedToPayment: () -> Unit = {}
) {
    val colors = AppTheme.colors
    val typo   = AppTheme.typography

    var selectedAddressIndex by remember { mutableIntStateOf(0) }
    val cartBooks            = BookRepository.topPicks.take(3)
    val subtotalOriginal     = cartBooks.sumOf { it.originalPrice }
    val subtotal             = cartBooks.sumOf { it.price }
    val discount             = subtotalOriginal - subtotal

    Scaffold(
        containerColor = colors.background,
        topBar = {
            BookoraTopBar(
                title    = "Checkout",
                showBack = true,
                showCart = false,
                onBack   = onBack,
                actions  = {
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(end = 12.dp)) {
                        Icon(Icons.Filled.Lock, contentDescription = null, tint = colors.primary, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("100% Secure", style = typo.labelMedium, color = colors.primary)
                    }
                }
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier.fillMaxWidth().background(colors.background).padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OrangeButton(
                    text     = "Proceed to Payment",
                    onClick  = onProceedToPayment,
                    modifier = Modifier.fillMaxWidth(),
                    leadingContent = {
                        Icon(Icons.Filled.ArrowForward, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                )
                Spacer(modifier = Modifier.height(6.dp))
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                    Icon(Icons.Filled.Lock, contentDescription = null, tint = colors.textMuted, modifier = Modifier.size(12.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Your data is safe and secure", style = typo.caption, color = colors.textMuted)
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            StepIndicator(currentStep = 2)

            Spacer(modifier = Modifier.height(24.dp))

            // Address section
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("1. Delivery Address", style = typo.sectionTitle, color = colors.textPrimary)
                Text("+ Add New Address", style = typo.labelMedium, color = colors.primary, modifier = Modifier.clickable {})
            }

            Spacer(modifier = Modifier.height(12.dp))

            addresses.forEachIndexed { index, address ->
                AddressCard(address = address, isSelected = selectedAddressIndex == index, onSelect = { selectedAddressIndex = index })
                if (index < addresses.lastIndex) Spacer(modifier = Modifier.height(10.dp))
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Order items section
            Text("2. Order Items (${cartBooks.size})", style = typo.sectionTitle, color = colors.textPrimary)
            Spacer(modifier = Modifier.height(12.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, colors.border, RoundedCornerShape(12.dp))
                    .clip(RoundedCornerShape(12.dp))
            ) {
                Column {
                    cartBooks.forEachIndexed { index, book ->
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 14.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            AsyncImage(
                                model = book.coverUrl,
                                contentDescription = book.title,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.size(60.dp).clip(RoundedCornerShape(6.dp)).background(colors.surface)
                            )
                            Column(modifier = Modifier.weight(1f)) {
                                Text(book.title,  style = typo.labelMedium, color = colors.textPrimary, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                Text(book.author, style = typo.bodySmall,   color = colors.textMuted)
                                Text("Qty: 1",    style = typo.bodySmall,   color = colors.textMuted)
                            }
                            Text("₹${book.price}", style = typo.priceSmall, color = colors.accent, fontWeight = FontWeight.Bold)
                        }
                        if (index < cartBooks.lastIndex)
                            HorizontalDivider(color = colors.border, modifier = Modifier.padding(horizontal = 14.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text("3. Order Summary", style = typo.sectionTitle, color = colors.textPrimary)
            Spacer(modifier = Modifier.height(12.dp))

            OrderSummaryCard(
                subtotalLabel = "Subtotal (${cartBooks.size} items)",
                subtotalValue = "₹$subtotalOriginal",
                discountValue = "-₹$discount",
                totalValue    = "₹$subtotal"
            )

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun AddressCard(address: DeliveryAddress, isSelected: Boolean, onSelect: () -> Unit) {
    val colors = AppTheme.colors
    val typo   = AppTheme.typography

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(if (isSelected) 2.dp else 1.dp, if (isSelected) colors.primary else colors.border, RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .clickable { onSelect() }
            .background(colors.background)
            .padding(14.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            RadioSelector(selected = isSelected, onSelect = onSelect)

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(address.label, style = typo.bodyLarge, fontWeight = FontWeight.Bold, color = colors.textPrimary)
                    if (address.isDefault) {
                        Box(modifier = Modifier.border(1.dp, colors.primary, RoundedCornerShape(4.dp)).padding(horizontal = 6.dp, vertical = 2.dp)) {
                            Text("Default", style = typo.caption, color = colors.primary)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(address.name,    style = typo.bodySmall,   color = colors.textPrimary)
                Text(address.address, style = typo.bodySmall,   color = colors.textMuted, lineHeight = androidx.compose.ui.unit.TextUnit.Unspecified)
                Spacer(modifier = Modifier.height(4.dp))
                Row {
                    Text("Phone: ", style = typo.bodySmall, color = colors.textMuted)
                    Text(address.phone, style = typo.labelMedium, color = colors.textPrimary)
                }
            }

            Row(modifier = Modifier.clickable {}, verticalAlignment = Alignment.CenterVertically) {
                Text("Edit", style = typo.labelMedium, color = colors.primary)
                Spacer(modifier = Modifier.width(3.dp))
                Icon(Icons.Filled.Edit, contentDescription = "Edit", tint = colors.primary, modifier = Modifier.size(14.dp))
            }
        }
    }
}
