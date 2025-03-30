package com.example.appbanhangonline.views.categoryScreen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.appbanhangonline.viewmodels.cartViewModels.CartViewModel
import com.example.appbanhangonline.viewmodels.productsViewModels.ProductViewModel
import com.example.appbanhangonline.views.categoryScreen.subfuncCategoryScreen.CartBubbleScreen
import com.example.appbanhangonline.views.homeScreen.subfuncHomeScreen.ProductGrid
import com.example.appbanhangonline.views.homeScreen.subfuncHomeScreen.CategoryTabBar
import com.example.appbanhangonline.views.categoryScreen.subfuncCategoryScreen.SearchBar

@Composable
fun CartScreen(
    navController: NavHostController,
    userID: String,
    productViewModel: ProductViewModel = viewModel(),
    cartViewModel: CartViewModel = viewModel(),
) {
    // Quan sát toàn bộ danh sách sản phẩm từ ViewModel
    val allProducts by productViewModel.products.observeAsState(initial = listOf())

    // Danh sách các tag sản phẩm (sử dụng productTag)
    val productTags = listOf("Đồ ăn", "Đồ uống", "Tráng miệng", "Đồ ăn vặt")
    var selectedTagIndex by remember { mutableIntStateOf(0) }
    var searchTerm by remember { mutableStateOf("") }

    LaunchedEffect(searchTerm) {
        if (searchTerm.isNotEmpty()) {
            productViewModel.getProductsByName(searchTerm)
        }
    }

    // Lấy sản phẩm theo tag được chọn khi selectedTagIndex thay đổi
    LaunchedEffect(selectedTagIndex) {
        productViewModel.fetchProductsByTag(productTags[selectedTagIndex])
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Thanh tìm kiếm
        SearchBar(
            searchTerm = searchTerm,
            onSearchTermChange = { searchTerm = it },
            modifier = Modifier.padding(8.dp),
            onSearch = { searchTerm = it },
            navController = navController
        )
        // Thanh điều hướng danh mục sản phẩm sử dụng productTag
        CategoryTabBar(
            categories = productTags,
            selectedIndex = selectedTagIndex,
            onCategorySelected = { selectedTagIndex = it },
            modifier = Modifier.padding(vertical = 4.dp)
        )

        // Lọc sản phẩm dựa trên productTag và từ khóa tìm kiếm
        val filteredProducts = allProducts.filter { product ->
            product.productTag.any { it.equals(productTags[selectedTagIndex], ignoreCase = true) }
                    && (product.name.contains(searchTerm, ignoreCase = true)
                    || product.description.contains(searchTerm, ignoreCase = true))
        }

        // Cập nhật: truyền thêm productViewModel vào ProductGrid
        ProductGrid(
            products = filteredProducts,
            navController = navController,
            modifier = Modifier.fillMaxSize(),
            productViewModel = productViewModel
        )
    }

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

