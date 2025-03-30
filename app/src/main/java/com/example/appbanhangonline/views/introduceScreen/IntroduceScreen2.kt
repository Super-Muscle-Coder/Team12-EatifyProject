package com.example.appbanhangonline.views.introduceScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
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
fun IntroduceScreen2(navController: NavController) {
    var showSlogan10 by remember { mutableStateOf(false) }
    var showSlogan11 by remember { mutableStateOf(false) }
    var showSlogan20 by remember { mutableStateOf(false) }
    var showSlogan21 by remember { mutableStateOf(false) }
    var showImage1 by remember { mutableStateOf(false) } // Thêm biến trạng thái để kiểm soát hiển thị ảnh đầu tiên
    var showImage2 by remember { mutableStateOf(false) } // Thêm biến trạng thái để kiểm soát hiển thị ảnh thứ hai
    var showImage3 by remember { mutableStateOf(false) } // Thêm biến trạng thái để kiểm soát hiển thị ảnh thứ ba
    var showImage4 by remember { mutableStateOf(false) } // Thêm biến trạng thái để kiểm soát hiển thị ảnh thứ tư

    val totalPages = 4
    var currentPage by remember { mutableIntStateOf(2) }

    LaunchedEffect(Unit) {
        delay(800)
        showSlogan10 = true
        showSlogan11 = true
        showImage1 = true // Hiển thị ảnh đầu tiên sau khi slogan thứ nhất xuất hiện
        showImage2 = true // Hiển thị ảnh thứ hai sau khi slogan thứ nhất xuất hiện
        delay(1300)
        showSlogan20 = true
        showSlogan21 = true
        showImage3 = true // Hiển thị ảnh thứ hai sau khi slogan thứ hai xuất hiện
        showImage4 = true // Hiển thị ảnh thứ tư sau khi slogan thứ hai xuất hiện

    }

    val textColor = MaterialTheme.colorScheme.onBackground
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp
    val density = LocalDensity.current

    val sloganFontSize = with(density) { (screenWidth * 0.02f + screenHeight * 0.015f).toSp() }
    val imageSize = screenWidth * 0.2f + screenHeight * 0.2f // Kích thước ảnh

    val topSpacer = screenHeight * 0.04f
    val middleSpacer3 = screenHeight * 0.01f

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

            // Thêm ảnh đầu tiên sau khi slogan thứ nhất phần 2 kết thúc
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween // Sắp xếp ảnh ở hai bên
            ) {

                AnimatedVisibility(
                    visible = showImage1,
                    enter = fadeIn(animationSpec = tween(durationMillis = 1000)) +
                            slideInHorizontally(initialOffsetX = { -it }, animationSpec = tween(durationMillis = 1000)) +
                            scaleIn(initialScale = 0.8f, animationSpec = tween(durationMillis = 1000))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.cheff), // Thay bằng ID ảnh mới
                        contentDescription = null,
                        modifier = Modifier
                            .size(screenWidth * 0.1f + screenHeight * 0.12f) // Kích thước của ảnh
                            .clip(RoundedCornerShape(180.dp)) // Bo tròn góc ảnh
                            .rotate(-10F), // Xoay ảnh 10 độ
                        contentScale = ContentScale.Fit
                    )
                }

                AnimatedVisibility(
                    visible = showImage2,
                    enter = fadeIn(animationSpec = tween(durationMillis = 1000)) +
                            slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(durationMillis = 1000)) +
                            scaleIn(initialScale = 0.8f, animationSpec = tween(durationMillis = 1000))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.vegatable), // Thay bằng ID ảnh hiện tại
                        contentDescription = null,
                        modifier = Modifier
                            .size(imageSize) // Kích thước của ảnh
                            .clip(RoundedCornerShape(180.dp)) // Bo tròn góc ảnh
                            .rotate(10F) ,// Xoay ảnh 10 độ
                        contentScale = ContentScale.Fit
                    )
                }


            }

            Spacer(modifier=Modifier.height(middleSpacer3))


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

            // Thêm ảnh thứ hai sau khi slogan thứ hai phần 2 kết thúc
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start // Sắp xếp ảnh ở bên trái
            ) {
                AnimatedVisibility(
                    visible = showImage3,
                    enter = fadeIn(animationSpec = tween(durationMillis = 1000)) +
                            slideInHorizontally(initialOffsetX = { -it }, animationSpec = tween(durationMillis = 1000)) +
                            scaleIn(initialScale = 0.8f, animationSpec = tween(durationMillis = 1000))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.spagheti), // Thay bằng ID ảnh tương ứng
                        contentDescription = null,
                        modifier = Modifier
                            .size(screenWidth * 0.22f + screenHeight * 0.2f) // Kích thước của ảnh
                            .clip(RoundedCornerShape(180.dp)) // Bo tròn góc ảnh
                            .rotate(-10F) // Xoay ảnh 10 độ
                            ,
                        contentScale = ContentScale.Fit
                    )
                }

                AnimatedVisibility(
                    visible = showImage4,
                    enter = fadeIn(animationSpec = tween(durationMillis = 1000)) +
                            slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(durationMillis = 1000)) +
                            scaleIn(initialScale = 0.8f, animationSpec = tween(durationMillis = 1000))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.baker), // Thay bằng ID ảnh mới
                        contentDescription = null,
                        modifier = Modifier
                            .size(screenWidth * 0.1f + screenHeight * 0.15f) // Kích thước của ảnh
                            .clip(RoundedCornerShape(180.dp)) // Bo tròn góc ảnh
                            .rotate(10F), // Xoay ảnh 10 độ
                        contentScale = ContentScale.Fit
                    )
                }
            }

            //Spacer(modifier = Modifier.height(middleSpacer3))

            /*Box(
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
            }*/
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