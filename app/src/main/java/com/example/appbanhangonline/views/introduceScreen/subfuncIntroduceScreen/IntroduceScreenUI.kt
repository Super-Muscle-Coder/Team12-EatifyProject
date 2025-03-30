package com.example.appbanhangonline.views.introduceScreen.subfuncIntroduceScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun IntroduceScreenUI(
    currentPage: Int,
    totalPages: Int,
    backgroundImageId: Int,
    content: @Composable () -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Ảnh nền
        Image(
            painter = painterResource(id = backgroundImageId),
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Container chứa nội dung giới thiệu
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(screenHeight * 0.9f)
                .align(Alignment.Center)
                .padding(
                    top = screenHeight * 0.07f,
                    bottom = screenHeight * 0.03f
                )
                .clip(RoundedCornerShape(50.dp))
                .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.8f))
                .padding(9.dp)
        ) {
            content()
        }

        // Thanh đánh dấu trang
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            PageIndicator(currentPage = currentPage, totalPages = totalPages)
        }
    }
}