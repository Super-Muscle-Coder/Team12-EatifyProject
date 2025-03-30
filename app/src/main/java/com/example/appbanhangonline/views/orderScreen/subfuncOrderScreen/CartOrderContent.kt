package com.example.appbanhangonline.views.orderScreen.subfuncOrderScreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.appbanhangonline.models.Orders
import java.util.Locale

import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

@Composable
fun CartOrderContent(
    orders: List<Orders>,
    isLoading: Boolean,
    error: String?,
    paddingValues: PaddingValues,
    onNavigateToInvoiceDetail: (Orders) -> Unit // Callback to navigate
) {
    val snackbarHostState = remember { SnackbarHostState() } // Create SnackbarHostState
    val coroutineScope = rememberCoroutineScope() // Create a coroutine scope
    val cartOrders = orders.filter { it.orderType == "cart" }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) } // Connect snackbarHostState
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
                        text = "Đã xảy ra lỗi: $error",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
            cartOrders.isEmpty() -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Không có đơn hàng nào từ giỏ hàng.",
                        style = MaterialTheme.typography.bodyLarge
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
                    items(cartOrders) { order ->
                        CartOrderCard(
                            order = order,
                            onNavigateToInvoiceDetail = { selectedOrder ->
                                if (selectedOrder.items.isEmpty()) {
                                    // Launch a coroutine to show the SnackBar
                                    coroutineScope.launch {
                                        snackbarHostState.showSnackbar("Giỏ hàng hiện tại trống, đặt hàng ngay thôi.")
                                    }
                                } else {
                                    onNavigateToInvoiceDetail(selectedOrder)
                                }
                            }
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }
        }
    }
}


@Composable
fun CartOrderCard(order: Orders, onNavigateToInvoiceDetail: (Orders) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Thông tin đơn hàng
            Column {
                Text(
                    text = "Tổng giá trị: ${order.totalPrice} VND",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Trạng thái: ${order.status}",
                    style = MaterialTheme.typography.bodySmall,
                    color = when (order.status.lowercase(Locale.getDefault())) {
                        "pending" -> Color(0xFFFFA000)
                        "completed" -> Color(0xFF388E3C)
                        else -> MaterialTheme.colorScheme.onSurface
                    }
                )
            }

            // Nút mũi tên điều hướng
            IconButton(onClick = { onNavigateToInvoiceDetail(order) }) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "Xem chi tiết hóa đơn",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
