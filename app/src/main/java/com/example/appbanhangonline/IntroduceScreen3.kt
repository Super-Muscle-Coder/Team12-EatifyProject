package com.example.appbanhangonline

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import com.example.appbanhangonline.ui.theme.IntroduceScreenUI
import com.example.appbanhangonline.R

@Composable
fun IntroduceScreen3(navController: NavController) {
    var showSlogan1 by remember { mutableStateOf(false) }
    var showSlogan2 by remember { mutableStateOf(false) }
    var showSlogan3 by remember { mutableStateOf(false) }
    val totalPages = 4
    var currentPage by remember { mutableIntStateOf(3) }

    LaunchedEffect(Unit) {
        delay(500)
        showSlogan1 = true
        delay(1000)
        showSlogan2 = true
        delay(1000)
        showSlogan3 = true
    }

    val textColor = MaterialTheme.colorScheme.onBackground
    val accentColor = Color(0xFF87CEEB)
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp
    val density = LocalDensity.current

    val sloganFontSize = with(density) { (screenWidth * 0.025f + screenHeight * 0.015f).toSp() }
    val buttonFontSize = with(density) { (screenWidth * 0.025f + screenHeight * 0.01f).toSp() }

    val topSpacer = screenHeight * 0.2f
    val middleSpacer1 = screenHeight * 0.1f
    val middleSpacer2 = screenHeight * 0.1f
    val bottomSpacer = screenHeight * 0.05f

    IntroduceScreenUI(
        currentPage = currentPage,
        totalPages = totalPages,
        backgroundImageId = R.drawable.pizza // Thay bằng ID ảnh nền tương ứng
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(topSpacer))

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                this@Column.AnimatedVisibility(
                    visible = showSlogan1,
                    enter = fadeIn(animationSpec = tween(durationMillis = 1000)) +
                            slideInHorizontally(initialOffsetX = { -it }, animationSpec = tween(durationMillis = 1000)) +
                            scaleIn(initialScale = 0.8f, animationSpec = tween(durationMillis = 1000))
                ) {
                    Text(
                        text = "Savor the convenience, love the experience.",
                        fontSize = sloganFontSize,
                        color = textColor,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(middleSpacer1))

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                this@Column.AnimatedVisibility(
                    visible = showSlogan2,
                    enter = fadeIn(animationSpec = tween(durationMillis = 1000)) +
                            slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(durationMillis = 1000)) +
                            scaleIn(initialScale = 0.8f, animationSpec = tween(durationMillis = 1000))
                ) {
                    Text(
                        text = "Unlock a world of flavors with just one tap.",
                        fontSize = sloganFontSize,
                        color = textColor,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(middleSpacer2))

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                this@Column.AnimatedVisibility(
                    visible = showSlogan3,
                    enter = fadeIn(animationSpec = tween(durationMillis = 1000)) +
                            slideInHorizontally(initialOffsetX = { -it }, animationSpec = tween(durationMillis = 1000)) +
                            scaleIn(initialScale = 0.8f, animationSpec = tween(durationMillis = 1000))
                ) {
                    Text(
                        text = "Join us in making every meal a celebration!",
                        fontSize = sloganFontSize,
                        color = textColor,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { /* Xử lý khi người dùng bấm nút */ },
                colors = ButtonDefaults.buttonColors(containerColor = accentColor),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(10.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Get order now with Eatify!",
                    color = Color.White,
                    fontSize = buttonFontSize
                )
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = textColor,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 1.dp)
                    .clickable {
                        navController.popBackStack()
                        currentPage = (currentPage - 1).coerceAtLeast(0)
                    }
            )

            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Forward",
                tint = textColor,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 1.dp)
                    .clickable {
                        navController.navigate("nextScreen")
                        currentPage = (currentPage + 1).coerceAtMost(totalPages - 1)
                    }
            )
        }
    }
}