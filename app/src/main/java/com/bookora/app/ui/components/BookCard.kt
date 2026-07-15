package com.bookora.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.bookora.app.data.model.Book
import com.bookora.app.ui.theme.AppTheme

@Composable
fun BookCard(
    book: Book,
    modifier: Modifier = Modifier,
    onClick: (Book) -> Unit = {}
) {
    val colors = AppTheme.colors
    val typo   = AppTheme.typography

    Column(
        modifier = modifier
            .width(140.dp)
            .clickable { onClick(book) }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color(0xFFF0F0F5))
        ) {
            AsyncImage(
                model = book.coverUrl,
                contentDescription = book.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = book.title,
            style = typo.labelMedium,
            color = colors.textPrimary,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            text = book.author,
            style = typo.labelSmall,
            color = colors.textMuted,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(4.dp))

        Row(
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = "₹${book.price}",
                style = typo.priceSmall,
                color = colors.primary
            )
            OriginalPrice("₹${book.originalPrice}")
            Text(
                text = "${book.rating}★",
                style = typo.caption,
                color = colors.accent
            )
        }
    }
}
