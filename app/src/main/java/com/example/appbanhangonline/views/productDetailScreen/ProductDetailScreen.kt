package com.example.appbanhangonline.views.productDetailScreen

import androidx.compose.foundation.Image
import com.example.appbanhangonline.R
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.appbanhangonline.models.Products
import com.example.appbanhangonline.viewmodels.cartViewModels.CartViewModel
import com.example.appbanhangonline.viewmodels.productsViewModels.ProductViewModel
import com.example.appbanhangonline.views.productDetailScreen.subfuncDetailScreen.AddToCartButton
import com.example.appbanhangonline.views.productDetailScreen.subfuncDetailScreen.DescriptionBox

@Composable
fun ProductDetailScreen(
    navController: NavHostController,
    key: String,
    value: String,
    userID: String,
    productViewModel: ProductViewModel = viewModel(),
    cartViewModel : CartViewModel = viewModel()
) {
    var product by remember { mutableStateOf<Products?>(null) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(key, value) {
        productViewModel.fetchProductByAllKey(key, value,
            onSuccess = { prod ->
                product = prod
            },
            onFailure = { e ->
                error = e.message ?: "Error querying product"
            }
        )
    }

    if (error != null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = error ?: "Unknown error", color = Color.Red)
        }
    } else if (product != null) {
        // Gọi composable khác để hiển thị nội dung chi tiết sản phẩm
        ProductDetailScreenContent(navController, product!!, userID,cartViewModel = cartViewModel)
    } else {
        // Hiển thị loading nếu chưa có dữ liệu
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun ProductDetailScreenContent(
    navController: NavHostController,
    product: Products,
    userID: String,
    cartViewModel: CartViewModel   // Thêm tham số CartViewModel được truyền từ ProductDetailScreen
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp

    var isFavorite by remember { mutableStateOf(false) }
    // Sử dụng CartViewModel đã được truyền vào, không gọi viewModel() lại ở đây

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Nút Back
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier
                        .size(32.dp)
                        .clickable { navController.popBackStack() },
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }

            // Hình ảnh sản phẩm
            Image(
                painter = rememberAsyncImagePainter(product.posterURL),
                contentDescription = product.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(screenHeight * 0.3f)
                    .clip(RoundedCornerShape(16.dp))
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Tên sản phẩm và nút trái tim
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.weight(1f)
                )
                val heartIcon = if (isFavorite) {
                    painterResource(id = R.drawable.favourite)
                } else {
                    painterResource(id = R.drawable.unfavourite)
                }
                Icon(
                    painter = heartIcon,
                    contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites",
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .size(32.dp)
                        .clickable { isFavorite = !isFavorite }
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Nhà cung cấp
            Box(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f))
                    .clickable { /* Điều hướng đến Trang tài khoản của nhà cung cấp */ }
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = product.provider,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Giá và đánh giá
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Cost: VND${product.price}",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "⭐${product.rating}",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Mô tả sản phẩm
            DescriptionBox(
                description = product.description,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Nút "Add to Cart" và "Order Now"
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Đảm bảo cả hai nút đều chiếm không gian bằng cách bọc trong Box với weight
                Box(modifier = Modifier.weight(1f)) {
                    AddToCartButton(
                        userID = userID,
                        productID = product.productID,
                        unitPrice = product.price,
                        name = product.name,
                        cartViewModel = cartViewModel
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Box(modifier = Modifier.weight(1f)) {
                    Button(
                        onClick = {
                            // Điều hướng đến OrderConfirmationScreen
                            navController.navigate("orderConfirmationScreen/${product.productID}")
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF87CEEB),
                            contentColor = Color.White
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Order Now")
                    }
                }
            }
        }
    }
}

