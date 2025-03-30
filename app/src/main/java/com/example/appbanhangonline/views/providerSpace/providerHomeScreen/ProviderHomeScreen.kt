package com.example.appbanhangonline.views.providerSpace.providerHomeScreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.appbanhangonline.models.Orders
import com.example.appbanhangonline.database.ProviderDatabaseHelper

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProviderHomeScreen(email: String, password: String) {
    // Các state cần thiết
    var providerInfo by remember { mutableStateOf<com.example.appbanhangonline.models.Providers?>(null) }
    var orders by remember { mutableStateOf<List<Orders>>(emptyList()) }
    var loading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    // Sử dụng LaunchedEffect để gọi các hàm bất đồng bộ khi màn hình khởi động
    LaunchedEffect(key1 = email, key2 = password) {
        val dbHelper = ProviderDatabaseHelper()
        // Xác thực và lấy thông tin nhà cung cấp
        val provider = dbHelper.authenticateProviderAndFetchInfo(email, password)
        if (provider == null) {
            error = "Authentication failed. Please check your credentials."
            loading = false
        } else {
            providerInfo = provider  // Log thông tin đã được in trong hàm authenticateProviderAndFetchInfo
            // Lấy danh sách đơn hàng cho nhà cung cấp dựa trên tên (field "provider")
            orders = dbHelper.getOrdersForProvider(provider.provider)
            loading = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Welcome, ${providerInfo?.provider ?: "Provider"}") })
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                error != null -> {
                    Text(
                        text = "Error: $error",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                orders.isEmpty() -> {
                    Text(
                        text = "No orders available",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(8.dp)
                    ) {
                        items(orders) { order ->
                            OrderCard(order = order)
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun OrderCard(order: Orders) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Order ID: ${order.orderID}",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Divider(
                modifier = Modifier.padding(vertical = 8.dp),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )
            Text(
                text = "Product: ${order.productName}",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Quantity: ${order.quantity}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary
            )
            Text(
                text = "Total Price: ${order.totalPrice}₫",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
            Divider(
                modifier = Modifier.padding(vertical = 8.dp),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.Info,
                    contentDescription = "Order Status",
                    tint = when (order.status) {
                        "Pending" -> MaterialTheme.colorScheme.tertiary
                        "Completed" -> MaterialTheme.colorScheme.primary
                        "Cancelled" -> MaterialTheme.colorScheme.error
                        else -> MaterialTheme.colorScheme.onSurface
                    }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = order.status,
                    style = MaterialTheme.typography.bodyMedium,
                    color = when (order.status) {
                        "Pending" -> MaterialTheme.colorScheme.tertiary
                        "Completed" -> MaterialTheme.colorScheme.primary
                        "Cancelled" -> MaterialTheme.colorScheme.error
                        else -> MaterialTheme.colorScheme.onSurface
                    }
                )
            }
        }
    }
}