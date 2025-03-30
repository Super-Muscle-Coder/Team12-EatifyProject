package com.example.appbanhangonline.views.orderScreen

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.appbanhangonline.viewmodels.cartViewModels.CartViewModel
import com.example.appbanhangonline.views.orderScreen.subfuncOrderScreen.PaymentDropdownMenu
import com.example.appbanhangonline.views.orderScreen.subfuncOrderScreen.StyledTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartConfirmationScreen(
    navController: NavHostController,
    cartViewModel: CartViewModel = viewModel(),
    userID: String
) {
    var address by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    var paymentMethod by remember { mutableStateOf("Cash on Delivery") }
    var showDialog by remember { mutableStateOf(false) }

    // Cảnh báo lỗi
    val addressError = address.isBlank()
    val phoneError = phone.isBlank()

    // Quan sát dữ liệu giỏ hàng
    val cartItems by cartViewModel.cartItems.observeAsState(emptyList())

    // Tính tổng giá trị giỏ hàng
    val totalPrice = cartItems.sumOf { it.totalPrice }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Xác Nhận Giỏ Hàng") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState()) // Toàn bộ trang có thể cuộn dọc
                .padding(16.dp)
        ) {
            // Hiển thị từng sản phẩm trong giỏ hàng
            cartItems.forEach { cartItem ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = cartItem.name,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "${cartItem.quantity} x ${cartItem.unitPrice} VND",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "${cartItem.totalPrice} VND",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                    thickness = 1.dp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Hiển thị tổng giá trị giỏ hàng
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Tổng cộng",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "$totalPrice VND",
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Ô nhập thông tin
            StyledTextField(
                value = address,
                onValueChange = { address = it },
                label = "Địa chỉ giao hàng",
                isError = addressError,
                errorText = "Địa chỉ không được để trống"
            )
            Spacer(modifier = Modifier.height(8.dp))
            StyledTextField(
                value = phone,
                onValueChange = { phone = it },
                label = "Số điện thoại",
                isError = phoneError,
                errorText = "Số điện thoại không được để trống",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
            )
            Spacer(modifier = Modifier.height(8.dp))
            StyledTextField(
                value = notes,
                onValueChange = { notes = it },
                label = "Ghi chú (Không bắt buộc)",
                isError = false,
                errorText = ""
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Dropdown menu chọn phương thức thanh toán
            PaymentDropdownMenu(
                selectedPaymentMethod = paymentMethod,
                onPaymentMethodSelected = { method -> paymentMethod = method }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Nút xác nhận đặt hàng
            // Nút xác nhận đặt hàng
            val isOrderValid = cartItems.isNotEmpty() && !addressError && !phoneError
            Button(
                onClick = {
                    // Log kiểm tra userID ngay tại điểm nhấn nút
                    Log.d("CartConfirmation", "UserID received in CartConfirmationScreen: '$userID'")
                    if (userID.isBlank()) {
                        Log.e("CartConfirmation", "Error: userID is blank! Kiểm tra nguồn cấp từ WindowBubble hoặc lớp giao diện trước đó.")
                    }
                    if (isOrderValid) {
                        cartViewModel.placeOrder(
                            userID = userID,
                            address = address,
                            phone = phone,
                            notes = notes,
                            paymentMethod = paymentMethod,
                            onSuccess = {
                                Log.d("CartConfirmation", "Order placed successfully for userID: '$userID'")
                                showDialog = true // Hiển thị dialog đặt hàng thành công
                            },
                            onError = { exception ->
                                Log.e("CartConfirmation", "Error placing order: ${exception.message}")
                            },
                            orderType = "cart" // Đảm bảo đơn hàng được lưu với orderType = "cart"
                        )
                    } else {
                        Log.e("CartConfirmation", "Order validation failed: isOrderValid=$isOrderValid, cartItems=$cartItems")
                    }
                },
                enabled = isOrderValid,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Xác nhận đặt hàng")
            }

            // Dialog thông báo đặt hàng thành công
            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text("Đặt Hàng Thành Công") },
                    text = { Text("Giỏ hàng của bạn đã được thêm, tiếp tục mua sắm chứ?") },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                showDialog = false
                                // Điều hướng về HomeScreen
                                navController.navigate("homeScreen") {
                                    popUpTo(navController.graph.startDestinationId) { inclusive = true }
                                    launchSingleTop = true
                                }
                                println("DEBUG: Navigating to HomeScreen after adding cart order")
                            }
                        ) {
                            Text("Trở về trang chủ")
                        }
                    }
                )
            }
        }
    }
}

