package com.example.appbanhangonline.views.categoryScreen.subfuncCategoryScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CartBubble(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    // Kích thước của bong bóng
    val bubbleSize = 40.dp // Chỉnh kích thước mong muốn

    Box(
        modifier = modifier
            .size(bubbleSize)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primary)
            .clickable { onClick() }
    ) {
        Icon(
            imageVector = androidx.compose.material.icons.Icons.Default.ShoppingCart,
            contentDescription = "Cart",
            tint = Color.White,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
