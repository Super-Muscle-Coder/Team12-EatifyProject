package com.example.appbanhangonline.views.categoryScreen.subfuncCategoryScreen

import android.util.Log
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.appbanhangonline.R
import com.example.appbanhangonline.viewmodels.cartViewModels.CartViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun CartBubbleScreen(
    cartContent: @Composable () -> Unit,
    cartViewModel: CartViewModel,
    navController: NavHostController
) {
    // Lấy userID từ FirebaseAuth
    val userID = FirebaseAuth.getInstance().currentUser?.uid ?: ""

    // Log kiểm tra userID
    Log.d("CartBubbleScreen", "CartBubbleScreen initialized with userID: '$userID'")

    // Trạng thái mở rộng hay thu gọn
    var expanded by remember { mutableStateOf(false) }

    // Kích thước của bong bóng khi thu gọn
    val collapsedSize = 50.dp
    val expandedSize = 350.dp

    // Xác định màu của bong bóng dựa vào chế độ hiển thị
    val bubbleColor = if (!isSystemInDarkTheme()) {
        Color(0xFF37474F).copy(alpha = 0.9f)
    } else {
        Color(0xFFB0BEC5).copy(alpha = 0.8f)
    }

    // Transition cho hiệu ứng mở rộng/thu gọn
    val transition = androidx.compose.animation.core.updateTransition(targetState = expanded, label = "BubbleTransition")

    // Animation cho kích thước
    val size by transition.animateDp(
        label = "BubbleSize",
        transitionSpec = { tween(durationMillis = 500) }
    ) { isExpanded ->
        if (isExpanded) expandedSize else collapsedSize
    }

    // Animation cho góc bo
    val cornerRadius by transition.animateDp(
        label = "BubbleCornerRadius",
        transitionSpec = { tween(durationMillis = 500) }
    ) { isExpanded ->
        if (isExpanded) 16.dp else collapsedSize / 2
    }

    // Animation cho alpha của nội dung
    val contentAlpha by transition.animateFloat(
        label = "BubbleContentAlpha",
        transitionSpec = { tween(durationMillis = 300, delayMillis = 200) }
    ) { isExpanded ->
        if (isExpanded) 1f else 0f
    }

    // Animation cho alpha của biểu tượng shopping cart
    val cartIconAlpha by transition.animateFloat(
        label = "CartIconAlpha",
        transitionSpec = { tween(durationMillis = 300, delayMillis = 200) }
    ) { isExpanded ->
        if (isExpanded) 0f else 1f
    }

    // Lấy danh sách sản phẩm từ CartViewModel
    val cartItems by cartViewModel.cartItems.observeAsState(emptyList())

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {
        // Bong bóng chính
        Box(
            modifier = Modifier
                .padding(end = 5.dp, bottom = 16.dp)
                .requiredSize(size)
                .clip(RoundedCornerShape(cornerRadius))
                .background(bubbleColor)
                .clickable { expanded = true },
            contentAlignment = Alignment.Center
        ) {
            // Biểu tượng shopping cart chỉ hiển thị khi thu gọn hoàn toàn
            if (!expanded) {
                Icon(
                    imageVector = androidx.compose.material.icons.Icons.Default.ShoppingCart,
                    contentDescription = "Cart",
                    tint = Color.White,
                    modifier = Modifier
                        .requiredSize(collapsedSize * 0.6f)
                        .alpha(cartIconAlpha)
                )
            }
        }

        // Nội dung mở rộng
        if (expanded) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 5.dp, bottom = 16.dp)
                    .requiredSize(size)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    // Dấu trừ để thu gọn ở góc trên bên trái
                    Icon(
                        painter = painterResource(id = R.drawable.minuss),
                        contentDescription = "Collapse",
                        tint = Color.Gray,
                        modifier = Modifier
                            .size(24.dp)
                            .align(Alignment.TopStart)
                            .clickable { expanded = false }
                            .padding(4.dp)
                    )
                    // Nội dung giỏ hàng
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 32.dp)
                            .alpha(contentAlpha),
                        contentAlignment = Alignment.Center
                    ) {
                        if (cartItems.isEmpty()) {
                            Text(
                                text = "Giỏ hàng trống",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        } else {
                            // Sử dụng WindowBubble để hiển thị giao diện chi tiết giỏ hàng, cùng với khả năng cập nhật số lượng
                            WindowBubble(
                                cartViewModel = cartViewModel, // Truyền cartViewModel để cập nhật số lượng sản phẩm
                                navController = navController,  // Truyền navController để hỗ trợ điều hướng
                                userID = userID // Truyền userID thực tế
                            )
                        }
                    }
                }
            }
        }
    }
}


