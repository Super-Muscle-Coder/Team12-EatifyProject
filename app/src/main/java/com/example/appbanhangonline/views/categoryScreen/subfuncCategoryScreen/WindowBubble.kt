package com.example.appbanhangonline.views.categoryScreen.subfuncCategoryScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.appbanhangonline.models.Carts
import com.example.appbanhangonline.viewmodels.cartViewModels.CartViewModel

@Composable
fun WindowBubble(
    cartViewModel: CartViewModel,
    navController: NavHostController,
    userID: String
) {
    // Quan sát dữ liệu giỏ hàng từ CartViewModel
    val cartItems by cartViewModel.cartItems.observeAsState(emptyList())

    // Tính tổng giá của giỏ hàng dựa trên trạng thái hiện tại
    val totalPrice = cartItems.sumOf { it.totalPrice }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        cartItems.forEach { item ->
            CartItemRow(
                cartItem = item,
                onQuantityChanged = { newQuantity ->
                    if (newQuantity > 0) {
                        cartViewModel.updateQuantity(
                            userID = item.userID,
                            productID = item.productID,
                            quantity = newQuantity
                        )
                    } else {
                        cartViewModel.removeFromCart(
                            userID = item.userID,
                            productID = item.productID
                        )
                    }
                },
                onRemoveItem = {
                    cartViewModel.removeFromCart(
                        userID = item.userID,
                        productID = item.productID
                    )
                },
                onProductNameClick = {
                    navController.navigate("productDetail/productID/${item.productID}") {
                        launchSingleTop = true
                        popUpTo("cartScreen") { inclusive = false }
                    }
                }
            )
            Divider(
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Hiển thị tổng giá trị và nút thanh toán
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = "Tổng cộng", style = MaterialTheme.typography.titleMedium)
                Text(text = "$totalPrice VND", style = MaterialTheme.typography.titleMedium)
            }
            Button(
                onClick = {
                    navController.navigate("cartConfirmationScreen?userID=$userID")
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(text = "Tiến hành thanh toán")
            }
        }
    }
}



@Composable
fun CartItemRow(
    cartItem: Carts,
    onQuantityChanged: (Int) -> Unit, // Callback để xử lý thay đổi số lượng
    onRemoveItem: () -> Unit,          // Callback để xóa sản phẩm
    onProductNameClick: () -> Unit     // Callback để điều hướng đến ProductDetailScreen
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Sản phẩm được hiển thị dưới dạng link (clickable)
        Text(
            text = cartItem.name,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .weight(1f)
                .clickable {
                    onProductNameClick() // Giữ logic điều hướng bên trong đây
                }
        )

        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = "Đơn giá: ${cartItem.unitPrice} VND",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "Tổng: ${cartItem.totalPrice} VND",
                style = MaterialTheme.typography.bodySmall
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Button(
                    onClick = { onQuantityChanged(cartItem.quantity - 1) },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text(text = "-")
                }
                Text(
                    text = "${cartItem.quantity}",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                Button(
                    onClick = { onQuantityChanged(cartItem.quantity + 1) },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text(text = "+")
                }
            }
        }
        // Nút xóa sản phẩm
        IconButton(
            onClick = { onRemoveItem() }
        ) {
            Icon(
                imageVector = Icons.Default.Close,  // Biểu tượng "x"
                contentDescription = "Remove Item",
                tint = MaterialTheme.colorScheme.error
            )
        }
    }
}


