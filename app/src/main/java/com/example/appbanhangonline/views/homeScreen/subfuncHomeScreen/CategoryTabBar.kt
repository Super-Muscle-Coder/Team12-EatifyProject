package com.example.appbanhangonline.views.homeScreen.subfuncHomeScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CategoryTabBar(
    categories: List<String>,
    selectedIndex: Int,
    onCategorySelected: (Int) -> Unit,
    modifier: Modifier
) {
    val isDarkTheme = isSystemInDarkTheme()
    val selectedTextColor = if (isDarkTheme) Color.White else Color.Black
    val unselectedTextColor = if (isDarkTheme) Color.Gray else Color.Gray
    val selectedLineColor = if (isDarkTheme) Color.White else Color.Black

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        items(categories.size) { index ->
            val isSelected = index == selectedIndex
            val fontSize by animateFloatAsState(
                targetValue = if (isSelected) 15.sp.value else 13.sp.value,
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            )
            val textColor by animateColorAsState(
                targetValue = if (isSelected) selectedTextColor else unselectedTextColor,
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            )
            val offsetY by animateFloatAsState(
                targetValue = if (isSelected) -8f else 0f,
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            )

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .graphicsLayer(translationY = offsetY)
                    .clickable(
                        onClick = { onCategorySelected(index) },
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    )
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    TextButton(
                        onClick = { onCategorySelected(index) },
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = textColor
                        ),
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                    ) {
                        Text(
                            text = categories[index],
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
                                .width(80.dp) // Chiều rộng của dấu gạch chân
                                .height(2.dp) // Chiều cao (độ dày) của dấu gạch chân
                                .background(selectedLineColor) // Màu sắc của dấu gạch chân
                        )
                    }
                }
            }
        }
    }
}
