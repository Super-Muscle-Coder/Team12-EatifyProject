package com.example.appbanhangonline.views.providerSpace.templateGeneralPro

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
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.example.appbanhangonline.R

@Composable
fun NavBarPro(
    navController: NavHostController
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    val backgroundColor = Color.White // Màu nền mặc định của NavBar
    val selectedIconColor = Color.Black
    val unselectedIconColor = Color.Gray
    val selectedBackgroundColor = Color.LightGray

    // Danh sách mục điều hướng: điều chỉnh nút phù hợp với doanh nghiệp
    val items = listOf("Orders", "Product Manager", "Business Info")

    NavigationBar(
        containerColor = backgroundColor, // Màu nền cho NavBar
        modifier = Modifier
            .fillMaxWidth()
            .height(screenHeight * 0.10f) // 10% chiều cao màn hình
    ) {
        var selectedIndex by remember { mutableIntStateOf(0) } // Quản lý trạng thái mục được chọn

        items.forEachIndexed { index, item ->
            val isSelected = selectedIndex == index

            val iconSize by animateFloatAsState(
                targetValue = if (isSelected) 45.dp.value else 30.dp.value, // Kích thước lớn hơn khi được chọn
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
                            selectedIndex = index
                            when (index) {
                                0 -> navController.navigate("ordersScreen") {
                                    launchSingleTop = true
                                }
                                1 -> navController.navigate("productManagerScreen") {
                                    launchSingleTop = true
                                }
                                2 -> navController.navigate("businessInfoScreen") {
                                    launchSingleTop = true
                                }
                            }
                        },
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    )
                    .padding(vertical = 4.dp)
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
                            .background(if (isSelected) selectedBackgroundColor else Color.Transparent)
                            .padding(0.dp)
                    ) {
                        Image(
                            painter = painterResource(id = when (item) {
                                "Orders" -> R.drawable.clipboard // Icon cho mục đơn hàng
                                "Product Manager" -> R.drawable.fastfood // Icon cho mục quản lý sản phẩm
                                "Business Info" -> R.drawable.business // Icon cho mục thông tin doanh nghiệp
                                else -> R.drawable.clipboard
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
                                color = selectedIconColor,
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