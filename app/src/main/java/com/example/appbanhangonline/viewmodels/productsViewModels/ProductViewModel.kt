package com.example.appbanhangonline.viewmodels.productsViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.appbanhangonline.database.ProductsDatabaseHelper
import com.example.appbanhangonline.models.Products
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {

    private val productsDatabaseHelper = ProductsDatabaseHelper()
    private val db = FirebaseFirestore.getInstance()

    private val _products = MutableLiveData<List<Products>>()
    val products: LiveData<List<Products>> = _products

    // Chứa đối tượng Products được chọn để truyền giữa các màn hình
    var selectedProduct: Products? = null

    // Lấy tất cả sản phẩm
    fun fetchProducts() {
        productsDatabaseHelper.fetchProducts(
            onSuccess = { productList ->
                Log.d("ProductViewModel", "Fetched products: $productList")
                _products.value = productList
            },
            onFailure = { exception ->
                Log.e("ProductViewModel", "Error fetching products", exception)
            }
        )
    }

    // Lấy sản phẩm theo danh mục
    fun fetchProductsByCategory(category: String) {
        productsDatabaseHelper.fetchProductsByCategory(
            category,
            onSuccess = { productList ->
                Log.d("ProductViewModel", "Fetched products by category: $productList")
                _products.value = productList
            },
            onFailure = { exception ->
                Log.e("ProductViewModel", "Error fetching products by category", exception)
            }
        )
    }

    // Cập nhật getProductsByName: chuyển đổi từ khóa tìm kiếm về lowercase và trim khoảng trắng,
    // sau đó truy vấn dựa trên field nameLowercase.
    fun getProductsByName(searchTerm: String) {
        viewModelScope.launch {
            val lowerTerm = searchTerm.lowercase().trim()
            println("DEBUG: Search term after normalization = '$lowerTerm'")
            println("DEBUG: Firestore query range: [$lowerTerm, ${lowerTerm + "\uf8ff"}]")

            db.collection("products")
                .orderBy("nameLowercase") // Thêm orderBy
                .startAt(lowerTerm)
                .endAt(lowerTerm + "\uf8ff")
                .get()
                .addOnSuccessListener { result ->
                    println("DEBUG: Firestore query success, result size = ${result.size()}")
                    val productList = result.documents.mapNotNull { document ->
                        val product = document.toObject(Products::class.java)
                        println("DEBUG: Product found -> name = ${product?.name}, nameLowercase = ${product?.nameLowercase}")
                        product
                    }

                    // Chỉ cập nhật LiveData nếu có kết quả
                    if (productList.isNotEmpty()) {
                        _products.value = productList
                        println("DEBUG: LiveData updated, total products = ${_products.value?.size}")
                    } else {
                        println("DEBUG: Query returned no results, keeping previous suggestions.")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.e("ProductViewModel", "Error fetching products by name", exception)
                }
        }
    }


    // Xử lý lấy thông tin sản phẩm thông qua productID
    fun getProductById(productId: Int?): Products? {
        return products.value?.find { it.productID == productId }
    }

    // Thêm hàm lấy sản phẩm theo productTag
    fun fetchProductsByTag(tag: String) {
        productsDatabaseHelper.fetchProductsByTag(
            tag,
            onSuccess = { productList ->
                Log.d("ProductViewModel", "Fetched products by tag: $productList")
                _products.value = productList
            },
            onFailure = { exception ->
                Log.e("ProductViewModel", "Error fetching products by tag", exception)
            }
        )
    }
    fun fetchProductByAllKey(
        key: String,
        value: String,
        onSuccess: (Products?) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        // Ví dụ: nếu key là "productID" và value có thể chuyển thành Int,
        // ta sẽ query theo productID.
        if(key == "productID") {
            try {
                val id = value.toInt()
                db.collection("products")
                    .whereEqualTo("productID", id)
                    .get()
                    .addOnSuccessListener { result ->
                        // Lấy sản phẩm đầu tiên nếu có
                        val product = result.documents.firstOrNull()?.toObject(Products::class.java)
                        onSuccess(product)
                    }
                    .addOnFailureListener { exception ->
                        onFailure(exception)
                    }
            } catch (e: NumberFormatException) {
                onFailure(e)
            }
        } else if(key == "name") {
            // Nếu key là "name", ta có thể query theo "name" hoặc "nameLowercase"
            val lowerValue = value.lowercase().trim()
            db.collection("products")
                .whereEqualTo("nameLowercase", lowerValue)
                .get()
                .addOnSuccessListener { result ->
                    val product = result.documents.firstOrNull()?.toObject(Products::class.java)
                    // Nếu không tìm thấy, ta có thể thử lại với "name" chính xác
                    if (product == null) {
                        db.collection("products")
                            .whereEqualTo("name", value)
                            .get()
                            .addOnSuccessListener { result2 ->
                                val product2 = result2.documents.firstOrNull()?.toObject(Products::class.java)
                                onSuccess(product2)
                            }
                            .addOnFailureListener { e2 ->
                                onFailure(e2)
                            }
                    } else {
                        onSuccess(product)
                    }
                }
                .addOnFailureListener { exception ->
                    onFailure(exception)
                }
        } else if(key == "productTag") {
            // Nếu key là "productTag", ta sử dụng whereArrayContains
            db.collection("products")
                .whereArrayContains("productTag", value)
                .get()
                .addOnSuccessListener { result ->
                    val product = result.documents.firstOrNull()?.toObject(Products::class.java)
                    onSuccess(product)
                }
                .addOnFailureListener { exception ->
                    onFailure(exception)
                }
        } else {
            // Default: dựa trên key được truyền, ta thử query với whereEqualTo
            db.collection("products")
                .whereEqualTo(key, value)
                .get()
                .addOnSuccessListener { result ->
                    val product = result.documents.firstOrNull()?.toObject(Products::class.java)
                    onSuccess(product)
                }
                .addOnFailureListener { exception ->
                    onFailure(exception)
                }
        }
    }

}
