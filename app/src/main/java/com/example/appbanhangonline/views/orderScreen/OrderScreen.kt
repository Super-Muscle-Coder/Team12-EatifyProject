package com.example.appbanhangonline.views.orderScreen
//Mã ổn định
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.appbanhangonline.viewmodels.orderViewModels.OrderViewModel
import com.example.appbanhangonline.views.orderScreen.subfuncOrderScreen.CartOrderContent
import com.example.appbanhangonline.views.orderScreen.subfuncOrderScreen.OrderTabBar
import com.example.appbanhangonline.views.orderScreen.subfuncOrderScreen.QuickOrderContent

@Composable
fun OrderScreen(
    navController: NavHostController,
    userID: String,
    orderViewModel: OrderViewModel = viewModel()
) {
    val orders by orderViewModel.orders.observeAsState(emptyList())
    val isLoading by orderViewModel.loading.observeAsState(false)
    val error by orderViewModel.error.observeAsState(null)

    // Lấy giá trị savedStateHandle từ BackStackEntry nếu có
    val savedTab = navController.currentBackStackEntry
        ?.savedStateHandle?.get<Int>("selectedTab")

    // Biến trạng thái để lưu tab được chọn
    var selectedTab by remember { mutableIntStateOf(savedTab ?: 0) }

    // Đồng bộ savedStateHandle với selectedTab
    LaunchedEffect(savedTab) {
        if (savedTab != null) {
            selectedTab = savedTab
            navController.currentBackStackEntry?.savedStateHandle?.remove<Int>("selectedTab")
            println("DEBUG: Updated selectedTab from savedStateHandle = $savedTab")
        }
    }

    // Tải danh sách đơn hàng của người dùng
    LaunchedEffect(userID) {
        orderViewModel.getUserOrders(userID)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Thanh điều hướng tab
        OrderTabBar(
            selectedIndex = selectedTab,
            onTabSelected = { index ->
                selectedTab = index
                println("DEBUG: Tab changed to $selectedTab") // Log khi tab thay đổi
            }
        )

        // Hiển thị nội dung dựa trên tab đã chọn
        when (selectedTab) {
            0 -> QuickOrderContent(
                orders = orders.filter { it.orderType == "single" || it.orderType.isEmpty() },
                isLoading = isLoading,
                error = error,
                paddingValues = PaddingValues(16.dp)
            )
            1 -> CartOrderContent(
                orders = orders.filter { it.orderType == "cart" },
                isLoading = isLoading,
                error = error,
                paddingValues = PaddingValues(16.dp),
                onNavigateToInvoiceDetail = { selectedOrder ->
                    // Điều hướng đến màn hình chi tiết hóa đơn
                    navController.navigate("invoiceDetail/${selectedOrder.orderID}") {
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}



