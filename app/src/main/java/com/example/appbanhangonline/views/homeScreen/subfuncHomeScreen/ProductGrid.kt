package com.example.appbanhangonline.views.homeScreen.subfuncHomeScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.appbanhangonline.models.Products
import com.example.appbanhangonline.viewmodels.productsViewModels.ProductViewModel

@Composable
fun ProductGrid(
    products: List<Products>,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    productViewModel: ProductViewModel
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 6.dp)
    ) {
        items(products) { product ->
            ProductBox(
                product = product,
                onProductClick = {
                    // Đặt sản phẩm được chọn vào ViewModel
                    productViewModel.selectedProduct = product
                    // Điều hướng sang màn hình chi tiết với key và value
                    navController.navigate("productDetail/productID/${product.productID}") {
                        launchSingleTop = true
                    }
                },
                modifier = Modifier.padding(6.dp)
            )
        }
    }
}
