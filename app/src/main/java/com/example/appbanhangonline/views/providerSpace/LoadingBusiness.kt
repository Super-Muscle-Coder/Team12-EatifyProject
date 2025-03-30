package com.example.appbanhangonline.views.providerSpace

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.appbanhangonline.R
import kotlinx.coroutines.delay

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun LoadingBusiness(
    onContinue: () -> Unit,
    modifier: Modifier = Modifier
) {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp
    val screenHeightDp = configuration.screenHeightDp.dp

    val iconSize = screenWidthDp * 0.3f
    val textSize = (screenWidthDp * 0.05f).value.sp
    val promptTextSize = (screenWidthDp * 0.04f).value.sp

    var message by remember { mutableStateOf("Welcome back to your business !") }
    var messageVisible by remember { mutableStateOf(true) }
    var showPrompt by remember { mutableStateOf(false) }
    var isVisible by remember { mutableStateOf(true) } // Quản lý hiển thị overlay

    // Điều chỉnh các câu khẩu hiệu
    LaunchedEffect(Unit) {
        delay(2000)
        messageVisible = false
        delay(500)
        message = "We're preparing things for you ..."
        messageVisible = true
        delay(2000)
        messageVisible = false
        delay(500)
        message = "Let's make some business :)"
        messageVisible = true
        delay(1000)
        showPrompt = true
    }

    // Khi isVisible chuyển thành false, chờ exit animation hoàn thành rồi gọi onContinue()
    LaunchedEffect(isVisible) {
        if (!isVisible) {
            delay(1000) // Thời gian exit animation
            onContinue()
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White) // Nền trắng để che phủ giao diện bên dưới
            .zIndex(1f) // Đặt lớp này lên trên
            .clickable(enabled = showPrompt) {
                isVisible = false
            },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Biểu tượng trung tâm
            Image(
                painter = painterResource(id = R.drawable.icon), // Thay bằng icon của doanh nghiệp
                contentDescription = null,
                modifier = Modifier.size(iconSize)
            )

            Spacer(modifier = Modifier.height(screenHeightDp * 0.05f))

            // Hiển thị khẩu hiệu động
            AnimatedVisibility(
                visible = messageVisible,
                enter = fadeIn(animationSpec = tween(durationMillis = 500))
                    .plus(scaleIn(animationSpec = tween(durationMillis = 500))),
                exit = fadeOut(animationSpec = tween(durationMillis = 500))
                    .plus(scaleOut(animationSpec = tween(durationMillis = 500)))
            ) {
                Text(
                    text = message,
                    fontSize = textSize,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // Nhắc nhở người dùng chạm để tiếp tục
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
        ) {
            AnimatedVisibility(
                visible = showPrompt,
                enter = fadeIn(animationSpec = tween(durationMillis = 500)),
                exit = fadeOut(animationSpec = tween(durationMillis = 500))
            ) {
                Text(
                    text = "Tap to continue",
                    fontSize = promptTextSize,
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }
}