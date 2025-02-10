package com.example.appbanhangonline

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import com.example.appbanhangonline.ui.theme.IntroduceScreenUI
import com.example.appbanhangonline.R

@Composable
fun IntroduceScreen1(navController: NavController) {
    var showWelcome by remember { mutableStateOf(false) }
    var showSlogan by remember { mutableStateOf(false) }
    val totalPages = 4
    var currentPage by remember { mutableIntStateOf(1) }

    LaunchedEffect(Unit) {
        delay(500)
        showWelcome = true
        delay(1000)
        showSlogan = true
    }

    val textColor = MaterialTheme.colorScheme.onBackground
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp
    val density = LocalDensity.current

    val titleFontSize = with(density) { (screenWidth * 0.02f + screenHeight * 0.015f).toSp() }

    val topSpacer = screenHeight * 0.05f
    val middleSpacer = screenHeight * 0.325f

    IntroduceScreenUI(
        currentPage = currentPage,
        totalPages = totalPages,
        backgroundImageId = R.drawable.pasta // Thay bằng ID ảnh nền tương ứng
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(topSpacer))

            Box(
                contentAlignment = Alignment.TopStart,
                modifier = Modifier.fillMaxWidth()
            ) {
                this@Column.AnimatedVisibility(
                    visible = showWelcome,
                    enter = fadeIn(animationSpec = tween(durationMillis = 1000)) +
                            slideInHorizontally(initialOffsetX = { -it }, animationSpec = tween(durationMillis = 1000)) +
                            scaleIn(initialScale = 0.8f, animationSpec = tween(durationMillis = 1000))
                ) {
                    Text(
                        text = "Welcome to a New Era of Dining",
                        fontSize = titleFontSize,
                        color = textColor,
                        textAlign = TextAlign.Start
                    )
                }
            }

            Spacer(modifier = Modifier.height(middleSpacer))

            Box(
                contentAlignment = Alignment.BottomEnd,
                modifier = Modifier.fillMaxWidth()
            ) {
                this@Column.AnimatedVisibility(
                    visible = showSlogan,
                    enter = fadeIn(animationSpec = tween(durationMillis = 1000)) +
                            slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(durationMillis = 1000)) +
                            scaleIn(initialScale = 0.8f, animationSpec = tween(durationMillis = 1000))
                ) {
                    Text(
                        text = "Taste the World, One Bite at a Time",
                        fontSize = titleFontSize,
                        color = textColor,
                        textAlign = TextAlign.End
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))
        }

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = textColor,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 0.5.dp)
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
                    .padding(end = 0.5.dp)
                    .clickable {
                        navController.navigate("introduceScreen2")
                        currentPage = 2
                    }
            )
        }
    }
}