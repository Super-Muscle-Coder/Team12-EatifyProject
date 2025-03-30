package com.example.appbanhangonline.database

import com.example.appbanhangonline.models.Products
import com.google.firebase.firestore.FirebaseFirestore
import android.util.Log

class ProductsDatabaseHelper {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    // Thêm sản phẩm mới (dành cho phiên bản ứng dụng doanh nghiệp và thử nghiệm)
    fun addProduct(product: Products, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        db.collection("products")
            .add(product)
            .addOnSuccessListener {
                Log.d("ProductsDatabaseHelper", "Product added successfully")
                onSuccess()
            }
            .addOnFailureListener { e ->
                Log.w("ProductsDatabaseHelper", "Error adding product", e)
                onFailure(e)
            }
    }

    // Lấy danh sách sản phẩm (dành cho phiên bản ứng dụng người dùng)
    fun fetchProducts(onSuccess: (List<Products>) -> Unit, onFailure: (Exception) -> Unit) {
        db.collection("products")
            .get()
            .addOnSuccessListener { result ->
                val products = result.map { it.toObject(Products::class.java) }
                onSuccess(products)
            }
            .addOnFailureListener { e ->
                Log.w("ProductsDatabaseHelper", "Error fetching products", e)
                onFailure(e)
            }
    }

    // Cập nhật thông tin sản phẩm (Chỉ dành cho phiên bản thử nghiệm và doanh nghiệp)
    fun updateProduct(productId: String, updatedProduct: Map<String, Any>, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        db.collection("products").document(productId)
            .update(updatedProduct)
            .addOnSuccessListener {
                Log.d("ProductsDatabaseHelper", "Product updated successfully")
                onSuccess()
            }
            .addOnFailureListener { e ->
                Log.w("ProductsDatabaseHelper", "Error updating product", e)
                onFailure(e)
            }
    }

    // Xóa sản phẩm (Chỉ dành cho phiên bản thử nghiệm và doanh nghiệp)
    fun deleteProduct(productId: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        db.collection("products").document(productId)
            .delete()
            .addOnSuccessListener {
                Log.d("ProductsDatabaseHelper", "Product deleted successfully")
                onSuccess()
            }
            .addOnFailureListener { e ->
                Log.w("ProductsDatabaseHelper", "Error deleting product", e)
                onFailure(e)
            }
    }

    // Lấy danh sách sản phẩm theo danh mục
    fun fetchProductsByCategory(category: String, onSuccess: (List<Products>) -> Unit, onFailure: (Exception) -> Unit) {
        db.collection("products")
            .whereEqualTo("category", category)
            .get()
            .addOnSuccessListener { result ->
                val products = result.map { it.toObject(Products::class.java) }
                onSuccess(products)
            }
            .addOnFailureListener { e ->
                Log.w("ProductsDatabaseHelper", "Error fetching products by category", e)
                onFailure(e)
            }
    }

    // Lấy danh sách sản phẩm theo tag
    fun fetchProductsByTag(tag: String, onSuccess: (List<Products>) -> Unit, onFailure: (Exception) -> Unit) {
        db.collection("products")
            .whereArrayContains("productTag", tag)
            .get()
            .addOnSuccessListener { result ->
                val products = result.map { it.toObject(Products::class.java) }
                onSuccess(products)
            }
            .addOnFailureListener { e ->
                Log.w("ProductsDatabaseHelper", "Error fetching products by tag", e)
                onFailure(e)
            }
    }
}
