package com.bookora.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bookora.app.data.repository.BookRepository
import com.bookora.app.ui.components.*
import com.bookora.app.ui.theme.AppTheme

data class PaymentMethod(
    val id: String,
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val iconBg: Color,
    val iconTint: Color
)

private val paymentMethods = listOf(
    PaymentMethod("card",   "Credit / Debit Card", "Visa, Mastercard, RuPay, American Express", Icons.Filled.CreditCard,      Color(0xFFEEF0FF), Color(0xFF3D47E0)),
    PaymentMethod("upi",    "UPI",                 "Pay using UPI apps",                        Icons.Filled.QrCode,           Color(0xFFF0FFF4), Color(0xFF16A34A)),
    PaymentMethod("nb",     "Net Banking",          "Pay using your bank account",               Icons.Filled.AccountBalance,   Color(0xFFFFF8EC), Color(0xFFD97706)),
    PaymentMethod("wallet", "Wallets",              "Pay using wallet",                          Icons.Filled.Wallet,           Color(0xFFF5F0FF), Color(0xFF7C3AED)),
    PaymentMethod("cod",    "Cash on Delivery",     "Pay when you receive",                      Icons.Filled.Money,            Color(0xFFEFFEF0), Color(0xFF16A34A))
)

@Composable
fun PaymentScreen(
    onBack: () -> Unit = {},
    onPaySuccess: () -> Unit = {}
) {
    val colors = AppTheme.colors
    val typo   = AppTheme.typography

    var selectedMethod  by remember { mutableStateOf("card") }
    var cardNumber      by remember { mutableStateOf("") }
    var cardName        by remember { mutableStateOf("") }
    var expiry          by remember { mutableStateOf("") }
    var cvv             by remember { mutableStateOf("") }
    var saveCard        by remember { mutableStateOf(true) }

    val cartBooks        = BookRepository.topPicks.take(3)
    val subtotalOriginal = cartBooks.sumOf { it.originalPrice }
    val subtotal         = cartBooks.sumOf { it.price }
    val discount         = subtotalOriginal - subtotal

    Scaffold(
        containerColor = colors.background,
        topBar = {
            BookoraTopBar(
                title    = "Payment",
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
                    text     = "Pay  ₹$subtotal",
                    onClick  = onPaySuccess,
                    modifier = Modifier.fillMaxWidth(),
                    leadingContent = {
                        Icon(Icons.Filled.Lock, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                )
                Spacer(modifier = Modifier.height(6.dp))
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                    Icon(Icons.Filled.Lock, contentDescription = null, tint = colors.textMuted, modifier = Modifier.size(12.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Your payment information is safe and secure", style = typo.caption, color = colors.textMuted)
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

            StepIndicator(currentStep = 3)

            Spacer(modifier = Modifier.height(24.dp))

            Text("1. Select Payment Method", style = typo.sectionTitle, color = colors.textPrimary)
            Spacer(modifier = Modifier.height(12.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, colors.border, RoundedCornerShape(12.dp))
                    .clip(RoundedCornerShape(12.dp))
                    .background(colors.background)
            ) {
                Column {
                    paymentMethods.forEachIndexed { index, method ->
                        PaymentMethodRow(method = method, isSelected = selectedMethod == method.id, onSelect = { selectedMethod = method.id })
                        if (index < paymentMethods.lastIndex)
                            HorizontalDivider(color = colors.border, modifier = Modifier.padding(horizontal = 14.dp))
                    }
                }
            }

            if (selectedMethod == "card") {
                Spacer(modifier = Modifier.height(24.dp))
                Text("2. Card Details", style = typo.sectionTitle, color = colors.textPrimary)
                Spacer(modifier = Modifier.height(12.dp))
                CardDetailsForm(
                    cardNumber = cardNumber,
                    cardName   = cardName,
                    expiry     = expiry,
                    cvv        = cvv,
                    saveCard   = saveCard,
                    onCardNumberChange = { if (it.length <= 16) cardNumber = it.filter { c -> c.isDigit() } },
                    onCardNameChange   = { cardName = it },
                    onExpiryChange     = {
                        val digits = it.filter { c -> c.isDigit() }.take(4)
                        expiry = if (digits.length > 2) "${digits.take(2)}/${digits.drop(2)}" else digits
                    },
                    onCvvChange        = { if (it.length <= 3) cvv = it.filter { c -> c.isDigit() } },
                    onSaveCardChange   = { saveCard = it }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text("${if (selectedMethod == "card") "3" else "2"}. Order Summary", style = typo.sectionTitle, color = colors.textPrimary)
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
private fun PaymentMethodRow(method: PaymentMethod, isSelected: Boolean, onSelect: () -> Unit) {
    val colors = AppTheme.colors
    val typo   = AppTheme.typography

    Row(
        modifier = Modifier.fillMaxWidth().clickable { onSelect() }.padding(horizontal = 14.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        RadioSelector(selected = isSelected, onSelect = onSelect)

        Box(modifier = Modifier.size(42.dp).clip(RoundedCornerShape(8.dp)).background(method.iconBg), contentAlignment = Alignment.Center) {
            Icon(method.icon, contentDescription = method.title, tint = method.iconTint, modifier = Modifier.size(22.dp))
        }

        Column(modifier = Modifier.weight(1f)) {
            Text(method.title,    style = typo.labelMedium, color = colors.textPrimary)
            Text(method.subtitle, style = typo.caption,     color = colors.textMuted)
        }

        Icon(
            imageVector = if (isSelected) Icons.Filled.KeyboardArrowDown else Icons.Filled.ChevronRight,
            contentDescription = null,
            tint = colors.primary,
            modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
private fun CardDetailsForm(
    cardNumber: String,
    cardName: String,
    expiry: String,
    cvv: String,
    saveCard: Boolean,
    onCardNumberChange: (String) -> Unit,
    onCardNameChange: (String) -> Unit,
    onExpiryChange: (String) -> Unit,
    onCvvChange: (String) -> Unit,
    onSaveCardChange: (Boolean) -> Unit
) {
    val colors = AppTheme.colors
    val typo   = AppTheme.typography

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, colors.border, RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .background(colors.background)
            .padding(16.dp)
    ) {
        Text("Card Number", style = typo.caption, color = colors.textMuted)
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = formatCardNumber(cardNumber),
            onValueChange = onCardNumberChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("1234 5678 9012 3456", color = colors.textMuted) },
            trailingIcon = {
                Row(modifier = Modifier.padding(end = 10.dp), horizontalArrangement = Arrangement.spacedBy(4.dp), verticalAlignment = Alignment.CenterVertically) {
                    CardBrandChip("VISA", Color(0xFF1A1F71))
                    CardBrandChip("MC",   Color(0xFFEB001B))
                    CardBrandChip("RP",   Color(0xFF007DB5))
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = colors.border, focusedBorderColor = colors.primary)
        )

        Spacer(modifier = Modifier.height(14.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Column(modifier = Modifier.weight(1.8f)) {
                Text("Cardholder Name", style = typo.caption, color = colors.textMuted)
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = cardName, onValueChange = onCardNameChange,
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("John Doe", color = colors.textMuted) },
                    singleLine = true, shape = RoundedCornerShape(10.dp),
                    colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = colors.border, focusedBorderColor = colors.primary)
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Text("Expiry Date", style = typo.caption, color = colors.textMuted)
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = expiry, onValueChange = onExpiryChange,
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("MM/YY", color = colors.textMuted) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true, shape = RoundedCornerShape(10.dp),
                    colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = colors.border, focusedBorderColor = colors.primary)
                )
            }
            Column(modifier = Modifier.weight(0.9f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("CVV", style = typo.caption, color = colors.textMuted)
                    Spacer(modifier = Modifier.width(3.dp))
                    Icon(Icons.Filled.Info, contentDescription = null, tint = colors.textMuted, modifier = Modifier.size(12.dp))
                }
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = cvv, onValueChange = onCvvChange,
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("123", color = colors.textMuted) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                    singleLine = true, shape = RoundedCornerShape(10.dp),
                    colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = colors.border, focusedBorderColor = colors.primary)
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable { onSaveCardChange(!saveCard) }
        ) {
            AppCheckbox(checked = saveCard, onCheckedChange = onSaveCardChange)
            Spacer(modifier = Modifier.width(10.dp))
            Text("Save this card for faster payments", style = typo.bodySmall, color = colors.textPrimary)
        }
    }
}

@Composable
private fun CardBrandChip(label: String, color: Color) {
    Box(
        modifier = Modifier.clip(RoundedCornerShape(4.dp)).background(color).padding(horizontal = 5.dp, vertical = 2.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = label, fontSize = 9.sp, fontWeight = FontWeight.ExtraBold, color = Color.White)
    }
}

private fun formatCardNumber(raw: String) = raw.chunked(4).joinToString(" ")
