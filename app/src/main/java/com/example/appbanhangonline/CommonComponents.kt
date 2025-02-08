package com.example.appbanhangonline

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PageIndicator(currentPage: Int, totalPages: Int) {
    Row(
        modifier = Modifier
            .padding(20.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(totalPages) { page ->
            Box(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .size(if (page == currentPage) 12.dp else 8.dp)
                    .background(
                        color = if (page == currentPage) Color.Gray else Color.White,
                        shape = CircleShape
                    )
            )
        }
    }
}
