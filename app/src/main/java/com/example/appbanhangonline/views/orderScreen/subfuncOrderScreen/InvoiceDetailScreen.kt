package com.example.appbanhangonline.views.orderScreen.subfuncOrderScreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.appbanhangonline.models.Orders
import com.example.appbanhangonline.viewmodels.orderViewModels.OrderViewModel
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InvoiceDetailScreen(
    navController: NavHostController, // Thêm NavController để điều hướng
    userID: String, // Truyền userID để tìm đơn hàng
    orderID: String,
    onBackClick: () -> Unit, // Callback quay lại (nếu cần dùng riêng)
    onNavigateBackToCartTab: () -> Unit, // Callback để quay lại tab "Giỏ hàng đã mua"
    orderViewModel: OrderViewModel = viewModel()
) {
    val orders by orderViewModel.orders.observeAsState(emptyList())
    val isLoading by orderViewModel.loading.observeAsState(false)
    val error by orderViewModel.error.observeAsState(null)

    val selectedOrder = orders.find { it.orderID == orderID }

    LaunchedEffect(userID) {
        orderViewModel.getUserOrders(userID)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Chi tiết hóa đơn") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.previousBackStackEntry?.savedStateHandle?.set("selectedTab", 1)
                        navController.popBackStack()
                        println("DEBUG: Navigating back from InvoiceDetailScreen with selectedTab=1")
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Quay lại"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        when {
            isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            error != null -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Lỗi: $error",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
            selectedOrder == null -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Không tìm thấy thông tin hóa đơn.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    item {
                        Column(modifier = Modifier.padding(bottom = 16.dp)) {
                            Text(
                                text = "Tổng giá trị: ${selectedOrder.totalPrice} VND",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Trạng thái: ${selectedOrder.status}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = when (selectedOrder.status.lowercase(Locale.getDefault())) {
                                    "pending" -> MaterialTheme.colorScheme.secondary
                                    "completed" -> MaterialTheme.colorScheme.primary
                                    else -> MaterialTheme.colorScheme.error
                                }
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Ngày đặt: ${formatTimestamp(selectedOrder.timestamp)}",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                    items(selectedOrder.items) { item ->
                        val name = item["name"] as? String ?: "Unknown"
                        val quantity = when (val q = item["quantity"]) {
                            is Int -> q
                            is Long -> q.toInt()
                            else -> 0
                        }
                        val unitPrice = when (val p = item["unitPrice"]) {
                            is Double -> p
                            is Long -> p.toDouble()
                            else -> 0.0
                        }
                        val itemTotal = unitPrice * quantity

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        ) {
                            Text(
                                text = "Tên sản phẩm: $name | Đơn giá: ${unitPrice.toInt()} VND",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Số lượng: $quantity | Thành tiền: ${itemTotal.toInt()} VND",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f))
                        }
                    }
                }
            }
        }
    }
}

