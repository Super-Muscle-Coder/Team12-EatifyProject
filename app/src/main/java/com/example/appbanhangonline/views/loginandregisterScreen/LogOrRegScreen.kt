package com.example.appbanhangonline.views.loginandregisterScreen

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.appbanhangonline.views.introduceScreen.subfuncIntroduceScreen.IntroduceScreenUI
import com.example.appbanhangonline.R
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import com.example.appbanhangonline.MainActivityPro
import kotlinx.coroutines.delay

@Composable
fun LogOrRegScreen(navController: NavHostController) {
    val configuration = LocalConfiguration.current
    val context = LocalContext.current // Lấy Context ở đầu hàm để tránh lỗi Composable
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp
    val density = LocalDensity.current

    val topSpacer = screenHeight * 0.05f
    val bottomSpacer = screenHeight * 0.1f
    val headlineFontSize = with(density) { (screenWidth * 0.04f + screenHeight * 0.02f).toSp() }
    val bodyFontSize = with(density) { (screenWidth * 0.02f + screenHeight * 0.015f).toSp() }
    val buttonFontSize = with(density) { (screenWidth * 0.02f + screenHeight * 0.015f).toSp() }
    val imageSize = screenWidth * 0.15f + screenHeight * 0.1f // Xác định kích thước của ảnh icon dựa trên chiều rộng của màn hình

    // Xác định màu chữ dựa trên chế độ sáng/tối
    val textColor = MaterialTheme.colorScheme.onBackground
    val buttonWidth = screenWidth * 0.4f // Xác định chiều rộng của nút bấm dựa trên chiều rộng của màn hình

    // Biến trạng thái để điều khiển sự xuất hiện của các phần tử
    var showText by remember { mutableStateOf(false) }
    var showButtons by remember { mutableStateOf(false) }
    var showImage by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(1000)
        showImage = true
        showText = true
        showButtons = true
    }

    IntroduceScreenUI(
        currentPage = 0,
        totalPages = 0,
        backgroundImageId = R.drawable.pizza
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(topSpacer))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AnimatedVisibility(
                    visible = showImage,
                    enter = fadeIn(animationSpec = tween(durationMillis = 1000)) +
                            scaleIn(initialScale = 0.8f, animationSpec = tween(durationMillis = 1000))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.icon), // Thay thế bằng ID ảnh icon của bạn
                        contentDescription = null,
                        modifier = Modifier.size(imageSize)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                AnimatedVisibility(
                    visible = showText,
                    enter = fadeIn(animationSpec = tween(durationMillis = 800))
                ) {
                    Text(
                        text = "Welcome to Eatify!",
                        color = textColor,
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontSize = headlineFontSize,
                        ),
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                AnimatedVisibility(
                    visible = showText,
                    enter = fadeIn(animationSpec = tween(durationMillis = 800))
                ) {
                    Text(
                        text = "Enter personal details to your employee account",
                        color = textColor,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontSize = bodyFontSize,
                        ),
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(bottomSpacer))

            AnimatedVisibility(
                visible = showButtons,
                enter = fadeIn(animationSpec = tween(durationMillis = 800))
            ) {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = { navController.navigate("loginScreen") }, // Điều hướng đến LoginScreen
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF87CEEB)),
                            modifier = Modifier
                                .width(buttonWidth)
                                .height(60.dp)
                                .clip(RoundedCornerShape(50))
                        ) {
                            Text(text = "Sign in", color = Color.White, fontSize = buttonFontSize)
                        }

                        Spacer(modifier = Modifier.width(5.dp))

                        Button(
                            onClick = { navController.navigate("registerScreen") }, // Điều hướng đến RegisterScreen
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF87CEEB)),
                            modifier = Modifier
                                .width(buttonWidth)
                                .height(60.dp)
                                .clip(RoundedCornerShape(50))
                        ) {
                            Text(text = "Sign up", color = Color.White, fontSize = buttonFontSize)
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Dòng chữ "Are you a provider? Sign in now" với "Sign in now" là TextButton
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Are you a provider? ",
                            color = textColor,
                            style = MaterialTheme.typography.bodyMedium.copy(fontSize = bodyFontSize)
                        )
                        TextButton(
                            onClick = {
                                // Chuyển sang MainActivityPro bằng Intent
                                val intent = Intent(context, MainActivityPro::class.java)
                                context.startActivity(intent)
                            },
                        ) {
                            Text(
                                text = "Sign in now",
                                style = MaterialTheme.typography.bodyMedium.copy(fontSize = bodyFontSize),
                                color = Color(0xFF0000FF) // Màu xanh da trời đậm cố định
                            )
                        }
                    }
                }
            }
        }
    }
}
