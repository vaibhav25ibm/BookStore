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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.bookora.app.ui.components.PrimaryButton
import com.bookora.app.ui.theme.AppTheme

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onSignUpClick: () -> Unit
) {
    val colors = AppTheme.colors
    val typo   = AppTheme.typography

    var email           by remember { mutableStateOf("john.doe@email.com") }
    var password        by remember { mutableStateOf("password123") }
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(48.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "Welcome Back!", style = typo.titleXL, color = colors.textPrimary)
                Spacer(modifier = Modifier.height(6.dp))
                Text(text = "Login to continue to Bookora", style = typo.bodyMedium, color = colors.textMuted)
            }
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(60.dp))
                    .background(colors.promoBackground),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "📚", style = typo.displayLarge)
            }
        }

        Spacer(modifier = Modifier.height(36.dp))

        // Email
        Text(text = "Email", style = typo.labelMedium, color = colors.textPrimary)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(Icons.Filled.Email, contentDescription = null, tint = colors.textMuted)
            },
            placeholder = { Text("john.doe@email.com", color = colors.textMuted) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = colors.border,
                focusedBorderColor   = colors.primary
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Password
        Text(text = "Password", style = typo.labelMedium, color = colors.textPrimary)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                        contentDescription = null,
                        tint = colors.textMuted
                    )
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = colors.border,
                focusedBorderColor   = colors.primary
            )
        )

        Spacer(modifier = Modifier.height(8.dp))
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
            Text(
                text = "Forgot Password?",
                style = typo.labelMedium,
                color = colors.primary,
                modifier = Modifier.clickable {}
            )
        }

        Spacer(modifier = Modifier.height(28.dp))

        PrimaryButton(
            text     = "Login",
            onClick  = onLoginSuccess,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            HorizontalDivider(modifier = Modifier.weight(1f), color = colors.border)
            Text(text = "  or continue with  ", style = typo.bodySmall, color = colors.textMuted)
            HorizontalDivider(modifier = Modifier.weight(1f), color = colors.border)
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            SocialButton(label = "G", labelColor = Color(0xFFDB4437), modifier = Modifier.weight(1f)) {}
            SocialButton(label = "f", labelColor = Color(0xFF1877F2), modifier = Modifier.weight(1f)) {}
            SocialButton(label = "",  labelColor = colors.textPrimary, modifier = Modifier.weight(1f)) {}
        }

        Spacer(modifier = Modifier.height(32.dp))

        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Text(
                text = buildAnnotatedString {
                    append("Don't have an account? ")
                    withStyle(SpanStyle(color = colors.primary, fontWeight = FontWeight.SemiBold)) {
                        append("Sign Up")
                    }
                },
                style = typo.bodyMedium,
                color = colors.textMuted,
                modifier = Modifier.clickable { onSignUpClick() }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun SocialButton(
    label: String,
    labelColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val colors = AppTheme.colors
    val typo   = AppTheme.typography
    Box(
        modifier = modifier
            .height(52.dp)
            .border(1.dp, colors.border, RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .background(colors.background),
        contentAlignment = Alignment.Center
    ) {
        Text(text = label.ifEmpty { "" }, style = typo.titleLarge, color = labelColor)
    }
}
