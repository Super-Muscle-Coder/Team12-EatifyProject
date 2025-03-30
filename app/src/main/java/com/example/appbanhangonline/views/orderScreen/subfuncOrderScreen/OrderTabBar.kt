package com.example.appbanhangonline.views.orderScreen.subfuncOrderScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.*
import androidx.compose.ui.Alignment

@Composable
fun OrderTabBar(
    selectedIndex: Int,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    // Định nghĩa 2 mục cố định
    val tabs = listOf("Mua hàng nhanh", "Giỏ hàng đã mua")

    // Màu sắc dựa trên chủ đề
    val selectedTextColor = MaterialTheme.colorScheme.onBackground
    val unselectedTextColor = Color.Gray
    val selectedLineColor = MaterialTheme.colorScheme.onBackground

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        items(tabs.size) { index ->
            val isSelected = index == selectedIndex
            val fontSize by animateFloatAsState(
                targetValue = if (isSelected) 15f else 13f,
                animationSpec = tween(durationMillis = 300)
            )
            val textColor by animateColorAsState(
                targetValue = if (isSelected) selectedTextColor else unselectedTextColor,
                animationSpec = tween(durationMillis = 300)
            )
            val offsetY by animateFloatAsState(
                targetValue = if (isSelected) -8f else 0f,
                animationSpec = tween(durationMillis = 300)
            )
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .graphicsLayer { translationY = offsetY }
                    .clickable(
                        onClick = { onTabSelected(index) },
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    )
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    TextButton(
                        onClick = { onTabSelected(index) },
                        colors = ButtonDefaults.textButtonColors(contentColor = textColor),
                        modifier = Modifier.padding(horizontal = 8.dp)
                    ) {
                        Text(
                            text = tabs[index],
                            fontSize = fontSize.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    AnimatedVisibility(
                        visible = isSelected,
                        enter = fadeIn(animationSpec = tween(200)),
                        exit = fadeOut(animationSpec = tween(200))
                    ) {
                        Box(
                            modifier = Modifier
                                .width(80.dp)
                                .height(2.dp)
                                .background(selectedLineColor)
                        )
                    }
                }
            }
        }
    }
}
