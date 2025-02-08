package com.example.appbanhangonline.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appbanhangonline.PageIndicator
import com.example.appbanhangonline.R

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
            painter = painterResource(id = backgroundImageId), // Sử dụng ID ảnh nền tương ứng
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Container chứa nội dung giới thiệu
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(screenHeight * 0.83f) // 83% chiều cao màn hình
                .align(Alignment.Center)
                .padding(
                    top = screenHeight * 0.10f, // Cách cạnh trên 10% chiều cao màn hình
                    bottom = screenHeight * 0.07f // Cách cạnh dưới 7% chiều cao màn hình
                )
                .clip(RoundedCornerShape(16.dp)) // Bo tròn các góc
                .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.8f))
                .padding(16.dp) // Thêm padding bên trong hộp
        ) {
            content()
        }

        // Thanh đánh dấu trang
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(screenHeight * 0.07f) // 7% chiều cao màn hình
                .align(Alignment.BottomCenter)
                .padding(bottom = screenHeight * 0.02f),
            contentAlignment = Alignment.Center
        ) {
            PageIndicator(currentPage = currentPage, totalPages = totalPages)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun IntroduceScreenUIPreview() {
    AppBanHangOnlineTheme {
        IntroduceScreenUI(
            currentPage = 1,
            totalPages = 4,
            backgroundImageId = R.drawable.background // Sử dụng ID ảnh nền thử nghiệm
        ) {

        }
    }
}