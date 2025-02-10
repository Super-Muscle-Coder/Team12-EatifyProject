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
fun IntroduceScreen2(navController: NavController) {
    var showSlogan10 by remember { mutableStateOf(false) }
    var showSlogan11 by remember { mutableStateOf(false) }
    var showSlogan20 by remember { mutableStateOf(false) }
    var showSlogan21 by remember { mutableStateOf(false) }
    var showSlogan30 by remember { mutableStateOf(false) }
    val totalPages = 4
    var currentPage by remember { mutableIntStateOf(2) }

    LaunchedEffect(Unit) {
        delay(500)
        showSlogan10 = true
        //delay(1000)
        showSlogan11 = true
        delay(1000)
        showSlogan20 = true
        //delay(1000)
        showSlogan21 = true
        delay(1000)
        showSlogan30 = true
    }

    val textColor = MaterialTheme.colorScheme.onBackground
    val accentColor = MaterialTheme.colorScheme.primary
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp
    val density = LocalDensity.current

    val sloganFontSize = with(density) { (screenWidth * 0.02f + screenHeight * 0.015f).toSp() }

    val topSpacer = screenHeight * 0.05f
    val middleSpacer = screenHeight * 0.005f
    val middleSpacer1 = screenHeight * 0.18f
    val middleSpacer2 = screenHeight * 0.005f
    val middleSpacer3 = screenHeight * 0.18f

    IntroduceScreenUI(
        currentPage = currentPage,
        totalPages = totalPages,
        backgroundImageId = R.drawable.pho // Thay bằng ID ảnh nền tương ứng
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
                    visible = showSlogan10,
                    enter = fadeIn(animationSpec = tween(durationMillis = 1000)) +
                            slideInHorizontally(initialOffsetX = { -it }, animationSpec = tween(durationMillis = 1000)) +
                            scaleIn(initialScale = 0.8f, animationSpec = tween(durationMillis = 1000))
                ) {
                    Text(
                        text = "Quality you can taste...",
                        fontSize = sloganFontSize,
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
                    visible = showSlogan11,
                    enter = fadeIn(animationSpec = tween(durationMillis = 1000)) +
                            slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(durationMillis = 1000)) +
                            scaleIn(initialScale = 0.8f, animationSpec = tween(durationMillis = 1000))
                ) {
                    Text(
                        text = "...freshness you can trust.",
                        fontSize = sloganFontSize,
                        color = textColor,
                        textAlign = TextAlign.End
                    )
                }
            }

            Spacer(modifier = Modifier.height(middleSpacer1))
            Box(
                contentAlignment = Alignment.TopStart,
                modifier = Modifier.fillMaxWidth()
            ) {
                this@Column.AnimatedVisibility(
                    visible = showSlogan20,
                    enter = fadeIn(animationSpec = tween(durationMillis = 1000)) +
                            slideInHorizontally(initialOffsetX = { -it }, animationSpec = tween(durationMillis = 1000)) +
                            scaleIn(initialScale = 0.8f, animationSpec = tween(durationMillis = 1000))
                ) {
                    Text(
                        text = "From our kitchen to your table...",
                        fontSize = sloganFontSize,
                        color = textColor,
                        textAlign = TextAlign.Start
                    )
                }
            }
            Spacer(modifier = Modifier.height(middleSpacer2))
            Box(
                contentAlignment = Alignment.BottomEnd,
                modifier = Modifier.fillMaxWidth()
            ) {
                this@Column.AnimatedVisibility(
                    visible = showSlogan21,
                    enter = fadeIn(animationSpec = tween(durationMillis = 1000)) +
                            slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(durationMillis = 1000)) +
                            scaleIn(initialScale = 0.8f, animationSpec = tween(durationMillis = 1000))
                ) {
                    Text(
                        text = "...freshness guaranteed.",
                        fontSize = sloganFontSize,
                        color = textColor,
                        textAlign = TextAlign.End
                    )
                }
            }

            Spacer(modifier = Modifier.height(middleSpacer3))
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                this@Column.AnimatedVisibility(
                    visible = showSlogan30,
                    enter = fadeIn(animationSpec = tween(durationMillis = 1000)) +
                            slideInHorizontally(initialOffsetX = { -it }, animationSpec = tween(durationMillis = 1000)) +
                            scaleIn(initialScale = 0.8f, animationSpec = tween(durationMillis = 1000))
                ) {
                    Text(
                        text = "Your joy, our pride, one meal at a time.",
                        fontSize = sloganFontSize,
                        color = textColor,
                        textAlign = TextAlign.Center
                    )
                }
            }


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
                        navController.navigate("introduceScreen3")
                        currentPage = 3
                    }
            )
        }
    }
}
