package com.example.appbanhangonline.views.homeScreen.subfuncHomeScreen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.appbanhangonline.models.Products
import kotlinx.coroutines.delay
import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.animation.ExperimentalAnimationApi
import com.example.appbanhangonline.viewmodels.productsViewModels.ProductViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SlideProduct(
    products: List<Products>,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    productViewModel: ProductViewModel
) {
    // Kiểm tra danh sách sản phẩm
    if (products.isEmpty()) {
        Log.e("SlideProduct", "Danh sách sản phẩm trống")
        return
    }

    // Lấy 5 sản phẩm ngẫu nhiên
    val randomProducts = remember(products) { products.shuffled().take(5) }

    var currentIndex by remember { mutableIntStateOf(0) }
    // Biến để tích lũy dragAmount.x
    var dragAccumulated by remember { mutableFloatStateOf(0f) }

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    // Đặt chiều cao cố định cho slide bằng 25% chiều cao màn hình
    val slideHeight = screenHeight * 0.25f

    // Hiệu ứng tự chuyển slide nếu không có thao tác từ người dùng
    LaunchedEffect(currentIndex) {
        delay(3500L)
        // Chỉ thay đổi currentIndex nếu không có swipe drag chiếm đủ ngưỡng,
        // tuy nhiên, nếu người dùng swipe, dragAccumulated sẽ được cập nhật
        currentIndex = (currentIndex + 1) % randomProducts.size
    }

    val isDarkTheme = isSystemInDarkTheme()

    Box(
        modifier = modifier
            .height(slideHeight)
            //.padding(8.dp)
            .border(
                width = 2.dp,
                color = if (isDarkTheme) Color.White else Color.Gray,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(8.dp)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDrag = { change, dragAmount ->
                        dragAccumulated += dragAmount.x
                        // Thiết lập một ngưỡng, ví dụ 50f
                        val threshold = 50f
                        if (dragAccumulated > threshold) {
                            currentIndex = if (currentIndex - 1 < 0) randomProducts.size - 1 else currentIndex - 1
                            dragAccumulated = 0f // Reset sau khi cập nhật
                            change.consume()
                        } else if (dragAccumulated < -threshold) {
                            currentIndex = (currentIndex + 1) % randomProducts.size
                            dragAccumulated = 0f
                            change.consume()
                        }
                    },
                    onDragEnd = {
                        dragAccumulated = 0f
                    }
                )
            }
    ) {
        val product = randomProducts[currentIndex]
        AnimatedContent(
            targetState = product,
            transitionSpec = {
                fadeIn(tween(300)) with fadeOut(tween(300))
            }
        ) { targetProduct ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        Log.d("SlideProduct", "Điều hướng đến trang chi tiết sản phẩm với ID: ${targetProduct.productID}")
                        // Đặt sản phẩm được chọn vào ViewModel
                        productViewModel.selectedProduct = targetProduct
                        // Điều hướng sang màn hình chi tiết với key và value
                        navController.navigate("productDetail/productID/${targetProduct.productID}") {
                            launchSingleTop = true
                        }
                    }
                    .padding(4.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Ảnh sản phẩm chiếm 40% chiều cao slide
                Image(
                    painter = rememberAsyncImagePainter(model = targetProduct.posterURL),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(slideHeight * 0.5f)
                        .clip(RoundedCornerShape(16.dp))
                )
                // Phần thông tin sản phẩm chiếm phần còn lại (60%)
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = targetProduct.name,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "$${targetProduct.price}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "⭐ ${targetProduct.rating}",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
        // Thanh đánh dấu trang (page indicator)
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(4.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            for (i in randomProducts.indices) {
                val indicatorColor = if (i == currentIndex) MaterialTheme.colorScheme.primary else Color.Gray
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .padding(horizontal = 2.dp)
                        .clip(CircleShape)
                        .background(indicatorColor)
                )
            }
        }
    }
}
