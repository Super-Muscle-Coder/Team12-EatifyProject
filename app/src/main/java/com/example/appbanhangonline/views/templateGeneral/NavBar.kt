package com.example.appbanhangonline.views.templateGeneral

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.example.appbanhangonline.R
import com.example.appbanhangonline.viewmodels.navBarViewModels.NavBarViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun NavBar(
    navController: NavHostController,
    navBarViewModel: NavBarViewModel
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val isDarkTheme = isSystemInDarkTheme()
    val selectedIndex by navBarViewModel.selectedIndex.collectAsState()
    val currentUser = FirebaseAuth.getInstance().currentUser

    val backgroundColor = if (isDarkTheme) Color.Black else Color.White
    val selectedIconColor = if (isDarkTheme) Color.Black else Color.White
    val unselectedIconColor = if (isDarkTheme) Color.LightGray else Color.Gray
    val selectedBackgroundColor = if (isDarkTheme) Color.White else Color.Black

    // Thêm "Order" vào danh sách items
    val items = listOf("Home", "Cart", "Order", "Profile")

    NavigationBar(
        containerColor = backgroundColor,
        modifier = Modifier
            .fillMaxWidth()
            .height(screenHeight * 0.10f) // 10% chiều cao màn hình
    ) {
        items.forEachIndexed { index, item ->
            val isSelected = selectedIndex == index
            val iconSize by animateFloatAsState(
                targetValue = if (isSelected) 45.dp.value else 30.dp.value,
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            )
            val iconColor by animateColorAsState(
                targetValue = if (isSelected) selectedIconColor else unselectedIconColor,
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            )
            val itemBackgroundColor by animateColorAsState(
                targetValue = if (isSelected) selectedBackgroundColor else Color.Transparent,
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            )
            val offsetY by animateFloatAsState(
                targetValue = if (isSelected) -25f else 0f, // Di chuyển lên trên khi được chọn
                animationSpec = tween(
                    durationMillis = 200,
                    easing = LinearOutSlowInEasing
                )
            )

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .clickable(
                        onClick = {
                            navBarViewModel.selectIndex(index)
                            when (index) {
                                0 -> navController.navigate("homeScreen") {
                                    popUpTo(navController.graph.startDestinationId)
                                    launchSingleTop = true
                                }
                                1 -> navController.navigate("cartScreen") {
                                    popUpTo(navController.graph.startDestinationId)
                                    launchSingleTop = true
                                }
                                2 -> navController.navigate("orderScreen") { // Thêm logic điều hướng đến OrderScreen
                                    popUpTo(navController.graph.startDestinationId)
                                    launchSingleTop = true
                                }
                                3 -> currentUser?.let {
                                    navController.navigate("profileScreen/${it.uid}") {
                                        popUpTo(navController.graph.startDestinationId)
                                        launchSingleTop = true
                                    }
                                }
                            }
                        },
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    )
                    .padding(vertical = 4.dp)
                    .graphicsLayer(alpha = if (isSelected) 1f else 1f, translationY = offsetY)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(iconSize.dp)
                            .clip(CircleShape)
                            .background(itemBackgroundColor)
                            .padding(0.dp)
                    ) {
                        Image(
                            painter = painterResource(id = when (item) {
                                "Home" -> R.drawable.home
                                "Cart" -> R.drawable.shoppingcartt
                                "Order" -> R.drawable.clipboard // Sử dụng biểu tượng danh sách từ R.drawable
                                "Profile" -> R.drawable.user
                                else -> R.drawable.home
                            }),
                            contentDescription = item,
                            colorFilter = ColorFilter.tint(iconColor),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    AnimatedVisibility(
                        visible = isSelected,
                        enter = fadeIn(animationSpec = tween(200)),
                        exit = fadeOut(animationSpec = tween(200))
                    ) {
                        Canvas(modifier = Modifier.size(24.dp, 5.dp)) {
                            drawArc(
                                color = selectedBackgroundColor,
                                startAngle = 0f,
                                sweepAngle = 180f,
                                useCenter = true
                            )
                        }
                    }
                }
            }
        }
    }
}

