package com.example.appbanhangonline.views.providerSpace.productManage

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.appbanhangonline.models.Products
import com.example.appbanhangonline.database.ProductsDatabaseHelper

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductManageScreen() {
    val dbHelper = ProductsDatabaseHelper()
    var products by remember { mutableStateOf<List<Products>>(emptyList()) }
    var loading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }
    var showAddDialog by remember { mutableStateOf(false) }
    var showUpdateDialog by remember { mutableStateOf(false) }
    var productToUpdate by remember { mutableStateOf<Products?>(null) }

    // Lấy danh sách sản phẩm khi screen load
    LaunchedEffect(Unit) {
        dbHelper.fetchProducts(
            onSuccess = { fetchedProducts ->
                products = fetchedProducts
                loading = false
            },
            onFailure = { e ->
                error = e.message
                loading = false
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Product Management") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddDialog = true }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Product")
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
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
                products.isEmpty() -> {
                    Text(
                        text = "No products available",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(products) { product ->
                            ProductCard(
                                product = product,
                                onEdit = {
                                    productToUpdate = product
                                    showUpdateDialog = true
                                },
                                onDelete = {
                                    // Xóa sản phẩm và refresh lại list
                                    dbHelper.deleteProduct(productId = product.productID.toString(),
                                        onSuccess = {
                                            // Cập nhật lại list sau khi xóa
                                            dbHelper.fetchProducts(
                                                onSuccess = { fetchedProducts -> products = fetchedProducts },
                                                onFailure = { e -> error = e.message }
                                            )
                                        },
                                        onFailure = { e -> error = e.message }
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    // Dialog thêm sản phẩm mới
    if (showAddDialog) {
        AddProductDialog(
            onDismiss = { showAddDialog = false },
            onAdd = { newProduct ->
                dbHelper.addProduct(newProduct,
                    onSuccess = {
                        // Sau khi thêm thành công, refresh lại list
                        dbHelper.fetchProducts(
                            onSuccess = { fetchedProducts -> products = fetchedProducts },
                            onFailure = { e -> error = e.message }
                        )
                        showAddDialog = false
                    },
                    onFailure = { e -> error = e.message }
                )
            }
        )
    }

    // Dialog cập nhật sản phẩm
    if (showUpdateDialog && productToUpdate != null) {
        UpdateProductDialog(
            product = productToUpdate!!,
            onDismiss = { showUpdateDialog = false },
            onUpdate = { updatedProduct ->
                // Sử dụng updateProduct, chuyển đổi updatedProduct thành map
                dbHelper.updateProduct(
                    productId = productToUpdate!!.productID.toString(),
                    updatedProduct = mapOf(
                        "name" to updatedProduct.name,
                        "description" to updatedProduct.description,
                        "price" to updatedProduct.price,
                        "posterURL" to updatedProduct.posterURL,
                        "category" to updatedProduct.category,
                        "productTag" to updatedProduct.productTag
                    ),
                    onSuccess = {
                        dbHelper.fetchProducts(
                            onSuccess = { fetchedProducts -> products = fetchedProducts },
                            onFailure = { e -> error = e.message }
                        )
                        showUpdateDialog = false
                        productToUpdate = null
                    },
                    onFailure = { e -> error = e.message }
                )
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductCard(
    product: Products,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Name: ${product.name}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Description: ${product.description}")
            Text(text = "Price: ${product.price}")
            Text(text = "Category: ${product.category}")
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = onEdit) {
                    Text(text = "Edit")
                }
                Spacer(modifier = Modifier.width(8.dp))
                TextButton(onClick = onDelete) {
                    Text(text = "Delete", color = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductDialog(onDismiss: () -> Unit, onAdd: (Products) -> Unit) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var posterURL by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    // Gỉa sử productTag để trống hoặc có thể nhập chuỗi phân cách cho nhiều tag

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Product") },
        text = {
            Column {
                TextField(value = name, onValueChange = { name = it }, label = { Text("Name") })
                TextField(value = description, onValueChange = { description = it }, label = { Text("Description") })
                TextField(
                    value = price,
                    onValueChange = { price = it },
                    label = { Text("Price") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                TextField(value = posterURL, onValueChange = { posterURL = it }, label = { Text("Poster URL") })
                TextField(value = category, onValueChange = { category = it }, label = { Text("Category") })
            }
        },
        confirmButton = {
            Button(onClick = {
                val newProduct = Products(
                    productID = 0, // Firestore tự sinh ID nếu cần
                    name = name,
                    description = description,
                    price = price.toDoubleOrNull() ?: 0.0,
                    provider = "", // Provider sẽ được gán theo logic của bạn
                    posterURL = posterURL,
                    category = category
                )
                onAdd(newProduct)
            }) {
                Text("Add")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateProductDialog(product: Products, onDismiss: () -> Unit, onUpdate: (Products) -> Unit) {
    var name by remember { mutableStateOf(product.name) }
    var description by remember { mutableStateOf(product.description) }
    var price by remember { mutableStateOf(product.price.toString()) }
    var posterURL by remember { mutableStateOf(product.posterURL) }
    var category by remember { mutableStateOf(product.category) }
    // Nếu có xử lý productTag thì có thể thêm TextField cho nó

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Update Product") },
        text = {
            Column {
                TextField(value = name, onValueChange = { name = it }, label = { Text("Name") })
                TextField(value = description, onValueChange = { description = it }, label = { Text("Description") })
                TextField(
                    value = price,
                    onValueChange = { price = it },
                    label = { Text("Price") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                TextField(value = posterURL, onValueChange = { posterURL = it }, label = { Text("Poster URL") })
                TextField(value = category, onValueChange = { category = it }, label = { Text("Category") })
            }
        },
        confirmButton = {
            Button(onClick = {
                val updatedProduct = product.copy(
                    name = name,
                    description = description,
                    price = price.toDoubleOrNull() ?: product.price,
                    posterURL = posterURL,
                    category = category
                )
                onUpdate(updatedProduct)
            }) {
                Text("Update")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}