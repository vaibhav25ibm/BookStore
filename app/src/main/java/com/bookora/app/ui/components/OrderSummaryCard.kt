package com.bookora.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bookora.app.ui.theme.AppTheme

/**
 * Shared order/cart summary block.
 * Used in CartScreen, CheckoutScreen, and PaymentScreen.
 *
 * @param subtotalLabel  Label shown next to subtotal (e.g. "Subtotal (3 items)").
 * @param subtotalValue  Formatted subtotal string.
 * @param discountValue  Formatted discount string (shown in red, e.g. "-₹200").
 * @param deliveryValue  Delivery charge string (e.g. "FREE").
 * @param totalValue     Total amount string (e.g. "₹699").
 */
@Composable
fun OrderSummaryCard(
    subtotalLabel: String,
    subtotalValue: String,
    discountValue: String,
    deliveryValue: String = "FREE",
    totalValue: String,
    modifier: Modifier = Modifier
) {
    val colors = AppTheme.colors
    val typo   = AppTheme.typography

    Column(
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, colors.border, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .background(colors.background)
            .padding(16.dp)
    ) {
        Text(
            text = "Order Summary",
            style = typo.sectionTitle,
            color = colors.textPrimary
        )

        Spacer(modifier = Modifier.height(14.dp))

        SummaryRow(label = subtotalLabel,    value = subtotalValue, valueColor = colors.textPrimary)
        Spacer(modifier = Modifier.height(10.dp))
        SummaryRow(label = "Discount",       value = discountValue, valueColor = colors.error)
        Spacer(modifier = Modifier.height(10.dp))
        SummaryRow(
            label = "Delivery Charges ⓘ",
            value = deliveryValue,
            valueColor = colors.success
        )

        Spacer(modifier = Modifier.height(14.dp))
        HorizontalDivider(color = colors.border)
        Spacer(modifier = Modifier.height(14.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Total Amount",
                style = typo.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = colors.textPrimary
            )
            Text(
                text = totalValue,
                style = typo.price,
                color = colors.accent
            )
        }
    }
}

@Composable
private fun SummaryRow(label: String, value: String, valueColor: Color) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = AppTheme.typography.bodyMedium, color = AppTheme.colors.textMuted)
        Text(text = value, style = AppTheme.typography.labelMedium, color = valueColor)
    }
}
