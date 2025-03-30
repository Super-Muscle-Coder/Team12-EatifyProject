package com.example.appbanhangonline.views.orderScreen

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.appbanhangonline.R
import com.example.appbanhangonline.models.Products
import com.example.appbanhangonline.viewmodels.orderViewModels.OrderViewModel
import com.example.appbanhangonline.views.orderScreen.subfuncOrderScreen.PaymentDropdownMenu
import com.example.appbanhangonline.views.orderScreen.subfuncOrderScreen.StyledTextField


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderConfirmationScreen(
    navController: NavHostController,
    product: Products,
    userID: String, // Truyền userID từ logic gọi
    orderViewModel: OrderViewModel = viewModel()
) {
    var quantity by remember { mutableIntStateOf(1) }
    var address by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    var paymentMethod by remember { mutableStateOf("Cash on Delivery") }
    var showDialog by remember { mutableStateOf(false) }

    // Cảnh báo lỗi
    val addressError = address.isBlank()
    val phoneError = phone.isBlank()

    // Tổng tiền tự động cập nhật
    val totalPrice = product.price * quantity

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Order Confirmation") },
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
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.headlineSmall // Cỡ chữ lớn hơn cho tên sản phẩm
                )
                Text(
                    text = "Price: \$${product.price}",
                    style = MaterialTheme.typography.titleMedium // Cỡ chữ lớn hơn cho đơn giá
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Điều chỉnh số lượng sản phẩm với hoạt ảnh
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { if (quantity > 1) quantity-- }) {
                    Icon(
                        painter = painterResource(id = R.drawable.minus), // Icon giảm số lượng
                        contentDescription = "Decrease Quantity",
                        modifier = Modifier.size(20.dp)
                    )
                }
                AnimatedContent(targetState = quantity) { targetQuantity ->
                    Text(text = "$targetQuantity", style = MaterialTheme.typography.bodyLarge)
                }
                IconButton(onClick = { quantity++ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.plus), // Icon tăng số lượng
                        contentDescription = "Increase Quantity",
                        modifier = Modifier.size(20.dp)
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Total: ",
                        style = MaterialTheme.typography.titleMedium // Cỡ chữ lớn hơn cho chữ "Total"
                    )
                    AnimatedContent(targetState = totalPrice) { targetTotalPrice ->
                        Text(
                            text = "\$${targetTotalPrice}",
                            style = MaterialTheme.typography.titleMedium // Cỡ chữ lớn hơn cho tổng giá trị
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Ô nhập thông tin
            StyledTextField(
                value = address,
                onValueChange = { address = it },
                label = "Shipping Address",
                isError = addressError,
                errorText = "Địa chỉ đang trống"
            )
            Spacer(modifier = Modifier.height(8.dp))
            StyledTextField(
                value = phone,
                onValueChange = { phone = it },
                label = "Phone Number",
                isError = phoneError,
                errorText = "Số điện thoại đang trống",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
            )
            Spacer(modifier = Modifier.height(8.dp))
            StyledTextField(
                value = notes,
                onValueChange = { notes = it },
                label = "Lưu ý cho người bán",
                isError = false,
                errorText = ""
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Dropdown menu
            PaymentDropdownMenu(
                selectedPaymentMethod = paymentMethod,
                onPaymentMethodSelected = { method -> paymentMethod = method }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Nút Place Order
            val isOrderValid = !addressError && !phoneError && quantity > 0
            Button(
                onClick = {
                    if (isOrderValid) {
                        orderViewModel.placeOrder(
                            userID = userID, // Truyền userID
                            product = product,
                            quantity = quantity,
                            address = address,
                            phone = phone,
                            notes = notes,
                            paymentMethod = paymentMethod
                        )
                        showDialog = true // Hiển thị dialog sau khi đặt hàng thành công
                    }
                },
                enabled = isOrderValid,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Place Order")
            }

            // Dialog thông báo đặt hàng thành công
            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text("Đặt Hàng Thành Công") },
                    text = { Text("Đơn hàng của bạn đã được tạo thành công!") },
                    confirmButton = {
                        if (showDialog) {
                            AlertDialog(
                                onDismissRequest = { showDialog = false },
                                title = { Text("Đặt Hàng Thành Công") },
                                text = { Text("Đơn hàng của bạn đã được tạo thành công!") },
                                confirmButton = {
                                    TextButton(
                                        onClick = {
                                            showDialog = false
                                            // Điều hướng đến OrderScreen với tab QuickOrderContent
                                            println("DEBUG: Navigating to OrderScreen with selectedTab=0")
                                            navController.navigate("orderScreen?selectedTab=0") {
                                                launchSingleTop = true // Đảm bảo không tạo thêm instance
                                            }
                                        }
                                    ) {
                                        Text("Xem đơn hàng")
                                    }
                                }
                            )
                        }
                    }
                )
            }
        }
    }
}



