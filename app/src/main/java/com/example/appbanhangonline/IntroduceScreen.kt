package com.example.appbanhangonline

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import com.example.appbanhangonline.ui.theme.IntroduceScreenUI
import com.example.appbanhangonline.R

@Composable
fun IntroduceScreen(navController: NavController) {
    var showImage by remember { mutableStateOf(false) }
    var showTitle by remember { mutableStateOf(false) }
    var showSlogan by remember { mutableStateOf(false) }
    val totalPages = 4
    var currentPage by remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        delay(1000)
        showImage = true
        delay(700)
        showTitle = true
        delay(700)
        showSlogan = true
    }

    val textColor = MaterialTheme.colorScheme.onBackground
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp
    val density = LocalDensity.current

    val imageSize = screenWidth * 0.15f + screenHeight * 0.1f

    val titleFontSize = with(density) { (screenWidth * 0.05f + screenHeight * 0.015f).toSp() }
    val sloganFontSize = with(density) { (screenWidth * 0.03f + screenHeight * 0.015f).toSp() }

    val imageOffset = screenHeight * 0.2f
    val titleOffset = screenHeight * 0.02f
    val sloganOffset = screenHeight * 0.01f

    IntroduceScreenUI(
        currentPage = currentPage,
        totalPages = totalPages,
        backgroundImageId = R.drawable.bread // Thay bằng ID ảnh nền tương ứng
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(imageOffset))

            Box(contentAlignment = Alignment.Center) {
                this@Column.AnimatedVisibility(
                    visible = showImage,
                    enter = fadeIn(animationSpec = tween(durationMillis = 1000)) +
                            scaleIn(initialScale = 0.8f, animationSpec = tween(durationMillis = 1000))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.icon),
                        contentDescription = null,
                        modifier = Modifier.size(imageSize)
                    )
                }
            }

            Spacer(modifier = Modifier.height(titleOffset))

            Box(contentAlignment = Alignment.Center) {
                this@Column.AnimatedVisibility(
                    visible = showTitle,
                    enter = fadeIn(animationSpec = tween(durationMillis = 800)) +
                            slideInVertically(
                                initialOffsetY = { it / 2 },
                                animationSpec = tween(durationMillis = 800)
                            )
                ) {
                    Text(
                        text = "Welcome to Eatify",
                        fontSize = titleFontSize,
                        textAlign = TextAlign.Center,
                        color = textColor
                    )
                }
            }

            Spacer(modifier = Modifier.height(sloganOffset))

            Box(contentAlignment = Alignment.Center) {
                this@Column.AnimatedVisibility(
                    visible = showSlogan,
                    enter = fadeIn(animationSpec = tween(durationMillis = 1000)) +
                            slideInVertically(
                                initialOffsetY = { it / 2 },
                                animationSpec = tween(durationMillis = 1000)
                            )
                ) {
                    Text(
                        text = "Make your meal more joy",
                        fontSize = sloganFontSize,
                        color = textColor.copy(alpha = 0.7f),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Forward",
                tint = textColor,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 0.5.dp)
                    .clickable {
                        navController.navigate("introduceScreen1")
                        currentPage = 1
                    }
            )
        }
    }
}