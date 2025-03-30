package com.example.appbanhangonline.views.homeScreen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.appbanhangonline.viewmodels.cartViewModels.CartViewModel
import com.example.appbanhangonline.viewmodels.productsViewModels.ProductViewModel
import com.example.appbanhangonline.views.categoryScreen.subfuncCategoryScreen.CartBubbleScreen
import com.example.appbanhangonline.views.homeScreen.subfuncHomeScreen.CategoryTabBar
import com.example.appbanhangonline.views.homeScreen.subfuncHomeScreen.ProductGrid
import com.example.appbanhangonline.views.homeScreen.subfuncHomeScreen.SlideProduct

@Composable
fun HomeScreen(
    navController: NavHostController,
    productViewModel: ProductViewModel,
    cartViewModel : CartViewModel = viewModel()
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    val categoryTabHeight = screenHeight * 0.1f
    val contentHeight = screenHeight * 0.9f

    val categories = listOf("Today's Offer", "Best Sellers", "May You Notice", "New Arrivals")
    var selectedCategoryIndex by rememberSaveable { mutableIntStateOf(0) }
    val selectedCategory = categories[selectedCategoryIndex]

    // Quan sát LiveData từ ViewModel
    val products by productViewModel.products.observeAsState(initial = emptyList())

    // Gọi fetchProductsByCategory khi selectedCategory thay đổi
    LaunchedEffect(selectedCategory) {
        productViewModel.fetchProductsByCategory(selectedCategory)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Thanh điều hướng trong HomeScreen
        CategoryTabBar(
            categories = categories,
            selectedIndex = selectedCategoryIndex,
            onCategorySelected = { selectedCategoryIndex = it },
            modifier = Modifier.padding(vertical = 4.dp)
        )

        // Gọi hàm SlideProduct để hiển thị các sản phẩm dưới dạng slide
        SlideProduct(
            products = products,
            navController = navController,
            productViewModel = productViewModel,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        // Nội dung hiển thị sản phẩm
        ProductGrid(
            products = products,
            navController = navController,
            productViewModel = productViewModel,
            modifier = Modifier
                .fillMaxWidth()
                .height(contentHeight - categoryTabHeight)
        )
    }

    // Thêm CartBubbleScreen
    CartBubbleScreen(
        cartContent = {
            // Nội dung giỏ hàng
            Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                Text(text = "Giỏ hàng của bạn", style = MaterialTheme.typography.titleLarge)

                val cartItems by cartViewModel.cartItems.observeAsState(emptyList())
                if (cartItems.isEmpty()) {
                    Text(
                        text = "Giỏ hàng trống",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                } else {
                    cartItems.forEach { item ->
                        Text(
                            text = "${item.productID}: ${item.quantity} x ${item.unitPrice} = ${item.totalPrice}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        },
        navController = navController,
        cartViewModel = cartViewModel // Truyền CartViewModel
    )
}

