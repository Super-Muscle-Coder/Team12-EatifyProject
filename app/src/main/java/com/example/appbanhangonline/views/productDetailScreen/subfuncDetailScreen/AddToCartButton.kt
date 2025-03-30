package com.example.appbanhangonline.views.productDetailScreen.subfuncDetailScreen

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import com.example.appbanhangonline.viewmodels.cartViewModels.CartViewModel

@Composable
fun AddToCartButton(
    userID: String,
    productID: Int,
    unitPrice: Double,
    name : String,
    cartViewModel: CartViewModel
) {
    var showDialog by remember { mutableStateOf(false) }

    // Sử dụng Row hoặc Column bao quanh nếu muốn dùng weight
    Row(modifier = Modifier.fillMaxWidth()) {
        Button(
            onClick = {
                // Gọi hàm thêm sản phẩm vào giỏ hàng
                cartViewModel.addToCart(
                    userID = userID,
                    productID = productID,
                    quantity = 1,
                    unitPrice = unitPrice,
                    specialRequest = "",
                    name = name
                )
                // Hiển thị Dialog thông báo
                showDialog = true
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Yellow, contentColor = Color.Black),
            modifier = Modifier.weight(1f) // Sử dụng weight bên trong Row
        ) {
            Text(text = "Add to Cart")
        }
    }

    // Hiển thị Dialog khi cần
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                Button(
                    onClick = { showDialog = false } // Đóng dialog khi nhấn "OK"
                ) {
                    Text(text = "OK")
                }
            },
            title = {
                Text(text = "Thông Báo")
            },
            text = {
                Text(text = "Thêm vào giỏ hàng thành công!")
            }
        )
    }
}
