package com.example.appbanhangonline.views.searchScreen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.appbanhangonline.models.Products
import com.example.appbanhangonline.viewmodels.cartViewModels.CartViewModel
import com.example.appbanhangonline.viewmodels.productsViewModels.ProductViewModel
import com.example.appbanhangonline.views.categoryScreen.subfuncCategoryScreen.CartBubbleScreen
import com.example.appbanhangonline.views.categoryScreen.subfuncCategoryScreen.SearchBar
import com.example.appbanhangonline.views.homeScreen.subfuncHomeScreen.ProductGrid

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavHostController,
    userID: String,
    cartViewModel : CartViewModel = viewModel(),
    productViewModel: ProductViewModel = viewModel(),
    searchTerm: String,
) {
    var mutableSearchTerm by remember { mutableStateOf(searchTerm) }

    LaunchedEffect(mutableSearchTerm) {
        if (mutableSearchTerm.isNotEmpty()) {
            productViewModel.getProductsByName(mutableSearchTerm)
        }
    }

    val suggestions by productViewModel.products.observeAsState(initial = listOf<Products>())

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text(text = "Search Products") },
            navigationIcon = {
                IconButton(onClick = {
                    navController.popBackStack("cartScreen", inclusive = false)
                }) {
                    Icon(
                        painter = painterResource(id = com.example.appbanhangonline.R.drawable.arrowleft),
                        contentDescription = "Back"
                    )
                }
            }
        )
        SearchBar(
            searchTerm = mutableSearchTerm,
            onSearchTermChange = { mutableSearchTerm = it },
            onSearch = { mutableSearchTerm = it },
            navController = navController
        )
        Spacer(modifier = Modifier.height(8.dp))
        ProductGrid(
            products = suggestions,
            navController = navController,
            modifier = Modifier.fillMaxSize(),
            productViewModel = productViewModel
        )
    }

    // Gọi CartBubbleScreen để hiển thị giỏ hàng
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

