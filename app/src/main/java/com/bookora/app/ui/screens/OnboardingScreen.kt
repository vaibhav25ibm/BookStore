package com.bookora.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bookora.app.ui.theme.BookoraMuted
import com.bookora.app.ui.theme.BookoraOrange
import com.bookora.app.ui.theme.BookoraPrimary

data class OnboardingPage(
    val title: String,
    val subtitle: String
)

val onboardingPages = listOf(
    OnboardingPage("Bookora", "Read more, grow more."),
    OnboardingPage("Discover Books", "Explore thousands of titles across every genre."),
    OnboardingPage("Start Reading", "Build your reading habit, one page at a time.")
)

@Composable
fun OnboardingScreen(onFinish: () -> Unit) {
    var currentPage by remember { mutableIntStateOf(0) }
    val page = onboardingPages[currentPage]

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Decorative background book icons
        BookBackgroundDecorations()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.weight(0.3f))

            // Logo icon
            Icon(
                imageVector = Icons.AutoMirrored.Filled.MenuBook,
                contentDescription = "Bookora Logo",
                tint = BookoraPrimary,
                modifier = Modifier.size(96.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // App name
            Text(
                text = page.title,
                fontSize = 38.sp,
                fontWeight = FontWeight.ExtraBold,
                color = BookoraPrimary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Tagline
            Text(
                text = page.subtitle,
                fontSize = 16.sp,
                color = BookoraMuted,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Page indicator dots
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                onboardingPages.forEachIndexed { index, _ ->
                    Box(
                        modifier = Modifier
                            .size(if (index == currentPage) 12.dp else 8.dp)
                            .clip(CircleShape)
                            .background(
                                if (index == currentPage) BookoraOrange
                                else BookoraMuted.copy(alpha = 0.35f)
                            )
                    )
                }
            }

            Spacer(modifier = Modifier.weight(0.7f))

            // Stacked books illustration (drawn with boxes)
            StackedBooksIllustration()

            Spacer(modifier = Modifier.height(40.dp))

            // Next / Get Started button
            Button(
                onClick = {
                    if (currentPage < onboardingPages.lastIndex) {
                        currentPage++
                    } else {
                        onFinish()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BookoraPrimary),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = if (currentPage < onboardingPages.lastIndex) "Next" else "Get Started",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun BookBackgroundDecorations() {
    // Decorative faint book icons in corners/background
    val positions = listOf(
        Pair(0.05f, 0.05f), Pair(0.75f, 0.07f),
        Pair(0.05f, 0.35f), Pair(0.80f, 0.32f),
        Pair(0.10f, 0.65f), Pair(0.65f, 0.60f),
        Pair(0.40f, 0.72f)
    )
    Box(modifier = Modifier.fillMaxSize()) {
        positions.forEach { (xFraction, yFraction) ->
            Icon(
                imageVector = Icons.AutoMirrored.Filled.MenuBook,
                contentDescription = null,
                tint = BookoraPrimary.copy(alpha = 0.08f),
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(
                        align = Alignment.TopStart,
                        unbounded = true
                    )
                    .offset(
                        x = (xFraction * 380).dp,
                        y = (yFraction * 820).dp
                    )
                    .size(42.dp)
            )
        }
    }
}

@Composable
private fun StackedBooksIllustration() {
    // Simple geometric stacked books using colored boxes
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(bottom = 8.dp)
    ) {
        // Book 3 (top) - dark blue
        Box(
            modifier = Modifier
                .width(160.dp)
                .height(28.dp)
                .clip(androidx.compose.foundation.shape.RoundedCornerShape(4.dp))
                .background(BookoraPrimary)
        ) {
            repeat(3) { i ->
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(2.dp)
                        .background(Color.White.copy(alpha = 0.3f))
                        .align(Alignment.CenterStart)
                        .offset(x = (30 + i * 20).dp)
                )
            }
        }
        // White page strip
        Box(
            modifier = Modifier
                .width(155.dp)
                .height(6.dp)
                .background(Color(0xFFF0F0F0))
        )
        // Book 2 (middle) - orange
        Box(
            modifier = Modifier
                .width(170.dp)
                .height(30.dp)
                .clip(androidx.compose.foundation.shape.RoundedCornerShape(4.dp))
                .background(BookoraOrange)
        )
        Box(
            modifier = Modifier
                .width(165.dp)
                .height(6.dp)
                .background(Color(0xFFF0F0F0))
        )
        // Book 1 (bottom) - dark blue (larger)
        Box(
            modifier = Modifier
                .width(180.dp)
                .height(32.dp)
                .clip(androidx.compose.foundation.shape.RoundedCornerShape(4.dp))
                .background(BookoraPrimary.copy(alpha = 0.85f))
        )
        // Shadow
        Box(
            modifier = Modifier
                .width(170.dp)
                .height(8.dp)
                .background(
                    BookoraPrimary.copy(alpha = 0.08f),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(50)
                )
        )
    }
}
