package com.example.appbanhangonline.views.introduceScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.appbanhangonline.R
import kotlinx.coroutines.delay
import com.example.appbanhangonline.views.introduceScreen.subfuncIntroduceScreen.IntroduceScreenUI

@Composable
fun IntroduceScreen3(navController: NavController) {
    var showSlogan10 by remember { mutableStateOf(false) }
    var showSlogan11 by remember { mutableStateOf(false) }
    var showImage1 by remember { mutableStateOf(false) }
    var showImage2 by remember { mutableStateOf(false) }
    var showSlogan20 by remember { mutableStateOf(false) }
    var showSlogan21 by remember { mutableStateOf(false) }
    var showImage3 by remember { mutableStateOf(false) }
    var showImage4 by remember { mutableStateOf(false) }
    var showSlogan30 by remember { mutableStateOf(false) }
    var showBtn by remember { mutableStateOf(false) }
    val totalPages = 4
    var currentPage by remember { mutableIntStateOf(3) }

    LaunchedEffect(Unit) {
        delay(1000)
        showSlogan10 = true
        showSlogan11 = true
        showImage1 = true
        showImage2 = true
        delay(1000)
        showSlogan20 = true
        showSlogan21 = true
        showImage3 = true
        showImage4 = true
        delay(1000)
        showSlogan30 = true
        delay(1000)
        showBtn = true
    }

    val textColor = MaterialTheme.colorScheme.onBackground
    val accentColor = Color(0xFF87CEEB)
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp
    val density = LocalDensity.current

    val sloganFontSize = with(density) { (screenWidth * 0.02f + screenHeight * 0.015f).toSp() }
    val buttonFontSize = with(density) { (screenWidth * 0.025f + screenHeight * 0.01f).toSp() }
    //val imageSize = screenWidth * 0.25f + screenHeight * 0.22f

    val topSpacer = screenHeight * 0.05f
    val middleSpacer = screenHeight * 0.02f
    val middleSpacer1 = screenHeight * 0.08f
    val middleSpacer2 = screenHeight * 0.005f
    val middleSpacer3 = screenHeight * 0.01f

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
                        text = "Savor the convenience",
                        fontSize = sloganFontSize,
                        color = textColor,
                        textAlign = TextAlign.Start
                    )
                }
            }

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
                        text = "Love the experience.",
                        fontSize = sloganFontSize,
                        color = textColor,
                        textAlign = TextAlign.End
                    )
                }
            }
            Spacer(modifier = Modifier.height(middleSpacer))
            // Thêm ảnh sau slogan thứ nhất phần 2
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween // Sắp xếp ảnh ở hai bên
            ) {
                // Ảnh từ bên trái
                AnimatedVisibility(
                    visible = showImage1,
                    enter = fadeIn(animationSpec = tween(durationMillis = 1000)) +
                            slideInHorizontally(initialOffsetX = { -it }, animationSpec = tween(durationMillis = 1000)) +
                            scaleIn(initialScale = 0.8f, animationSpec = tween(durationMillis = 1000))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.order), // Thay bằng ID ảnh mới
                        contentDescription = null,
                        modifier = Modifier
                            .size(screenWidth * 0.1f + screenHeight * 0.1f) // Kích thước của ảnh
                            //.clip(RoundedCornerShape(180.dp)) // Bo tròn góc ảnh
                            //.rotate(-10F) // Xoay ảnh -10 độ
                            ,
                        contentScale = ContentScale.Fit
                    )
                }

                // Ảnh từ bên phải
                AnimatedVisibility(
                    visible = showImage2,
                    enter = fadeIn(animationSpec = tween(durationMillis = 1000)) +
                            slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(durationMillis = 1000)) +
                            scaleIn(initialScale = 0.8f, animationSpec = tween(durationMillis = 1000))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.deliveryman), // Thay bằng ID ảnh hiện tại
                        contentDescription = null,
                        modifier = Modifier
                            .size(screenWidth * 0.1f + screenHeight * 0.1f), // Kích thước của ảnh
                            //.clip(RoundedCornerShape(180.dp)) // Bo tròn góc ảnh
                            //.rotate(10F) ,// Xoay ảnh 10 độ
                        contentScale = ContentScale.Fit
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
                        text = "Unlock a world of flavors",
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
                        text = "With just one tap.",
                        fontSize = sloganFontSize,
                        color = textColor,
                        textAlign = TextAlign.End
                    )
                }
            }
            Spacer(modifier = Modifier.height(middleSpacer))
            // Thêm ảnh sau slogan thứ nhất phần 2
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween // Sắp xếp ảnh ở hai bên
            ) {
                // Ảnh từ bên trái
                AnimatedVisibility(
                    visible = showImage3,
                    enter = fadeIn(animationSpec = tween(durationMillis = 1000)) +
                            slideInHorizontally(initialOffsetX = { -it }, animationSpec = tween(durationMillis = 1000)) +
                            scaleIn(initialScale = 0.8f, animationSpec = tween(durationMillis = 1000))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.shoppingcart), // Thay bằng ID ảnh mới
                        contentDescription = null,
                        modifier = Modifier
                            .size(screenWidth * 0.1f + screenHeight * 0.1f) // Kích thước của ảnh
                        //.clip(RoundedCornerShape(180.dp)) // Bo tròn góc ảnh
                        .rotate(-10F) // Xoay ảnh -10 độ
                        ,
                        contentScale = ContentScale.Fit
                    )
                }

                // Ảnh từ bên phải
                AnimatedVisibility(
                    visible = showImage4,
                    enter = fadeIn(animationSpec = tween(durationMillis = 1000)) +
                            slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(durationMillis = 1000)) +
                            scaleIn(initialScale = 0.8f, animationSpec = tween(durationMillis = 1000))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.menu), // Thay bằng ID ảnh hiện tại
                        contentDescription = null,
                        modifier = Modifier
                            .size(screenWidth * 0.1f + screenHeight * 0.1f) // Kích thước của ảnh
                        //.clip(RoundedCornerShape(180.dp)) // Bo tròn góc ảnh
                        .rotate(10F) ,// Xoay ảnh 10 độ
                        contentScale = ContentScale.Fit
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
                            slideInVertically(
                                initialOffsetY = { it / 2 },
                                animationSpec = tween(durationMillis = 1000)
                            )
                ) {
                    Text(
                        text = "Join us in making every meal!",
                        fontSize = sloganFontSize,
                        color = textColor,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.weight(0.5f))

            AnimatedVisibility(
                visible = showBtn,
                enter = fadeIn(animationSpec = tween(durationMillis = 1000)) +
                        scaleIn(initialScale = 0.8f, animationSpec = tween(durationMillis = 1000))
            ) {
                Button(
                    onClick = {
                        navController.navigate("logOrRegScreen") // Điều hướng đến màn hình LogOrRegScreen
                    },
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

        }

        Box(modifier = Modifier.fillMaxSize()) {
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

        }
    }
}
