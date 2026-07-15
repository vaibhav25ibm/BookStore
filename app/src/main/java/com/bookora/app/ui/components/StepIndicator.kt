package com.bookora.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bookora.app.ui.theme.AppTheme

/**
 * Horizontal step indicator used on Checkout and Payment screens.
 *
 * @param steps        Step label list, e.g. ["Cart", "Address", "Payment", "Review"].
 * @param currentStep  1-based index of the currently active step.
 */
@Composable
fun StepIndicator(
    steps: List<String> = listOf("Cart", "Address", "Payment", "Review"),
    currentStep: Int
) {
    val colors = AppTheme.colors
    val typo   = AppTheme.typography

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        steps.forEachIndexed { index, label ->
            val stepNumber = index + 1
            val isDone    = stepNumber < currentStep
            val isCurrent = stepNumber == currentStep

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(
                            when {
                                isDone || isCurrent -> colors.primary
                                else -> Color.White
                            }
                        )
                        .border(
                            2.dp,
                            if (isDone || isCurrent) colors.primary else colors.border,
                            CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (isDone) {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                    } else {
                        Text(
                            text = "$stepNumber",
                            style = typo.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = if (isCurrent) Color.White else colors.textMuted
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = label,
                    style = typo.caption,
                    fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal,
                    color = if (isCurrent) colors.primary else colors.textMuted,
                    textAlign = TextAlign.Center
                )
            }

            // Connector line
            if (index < steps.lastIndex) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(2.dp)
                        .padding(bottom = 0.dp)
                        .offset(y = (-8).dp)
                        .background(
                            if (stepNumber < currentStep) colors.primary else colors.border,
                            RoundedCornerShape(1.dp)
                        )
                )
            }
        }
    }
}
