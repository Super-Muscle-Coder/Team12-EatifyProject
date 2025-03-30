package com.example.appbanhangonline.database

import android.util.Log
import com.example.appbanhangonline.models.Orders
import com.example.appbanhangonline.models.Products
import com.example.appbanhangonline.models.Providers
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class ProviderDatabaseHelper {
    private val firestore = FirebaseFirestore.getInstance()

    /**
     * Xác thực nhà cung cấp dựa trên email và mật khẩu.
     * Nếu xác thực thành công, thông tin của nhà cung cấp được log đầy đủ trong Logcat
     * và trả về đối tượng Providers.
     */
    suspend fun authenticateProviderAndFetchInfo(email: String, password: String): Providers? {
        return try {
            val querySnapshot = firestore.collection("providers")
                .whereEqualTo("email", email)
                .get()
                .await()

            Log.d("ProviderDatabaseHelper", "Documents found for email ($email): ${querySnapshot.size()}")

            if (querySnapshot.isEmpty) {
                Log.e("ProviderDatabaseHelper", "No provider found for email: $email")
                null
            } else {
                val providerDoc = querySnapshot.documents[0]
                val storedPassword = providerDoc.getString("password") ?: ""

                Log.d("ProviderDatabaseHelper", "Stored password: $storedPassword, Input password: $password")

                if (storedPassword == password) {
                    // Log thông tin chi tiết của nhà cung cấp
                    logProviderInfo(providerDoc)
                    // Chuyển đổi document thành đối tượng Providers
                    providerDoc.toObject(Providers::class.java)
                } else {
                    Log.e("ProviderDatabaseHelper", "Password does not match for email: $email")
                    null
                }
            }
        } catch (e: Exception) {
            Log.e("ProviderDatabaseHelper", "Error authenticating provider: ${e.message}")
            null
        }
    }

    /**
     * Lấy danh sách sản phẩm thuộc về nhà cung cấp dựa trên tên của họ.
     */
    suspend fun getProductsByProvider(providerName: String): List<Products> {
        return try {
            val querySnapshot = firestore.collection("products")
                .whereEqualTo("provider", providerName)
                .get()
                .await()

            val productList = querySnapshot.documents.mapNotNull { document ->
                document.toObject(Products::class.java)
            }

            Log.d("ProviderDatabaseHelper", "Products for provider $providerName: $productList")
            productList
        } catch (e: Exception) {
            Log.e("ProviderDatabaseHelper", "Error fetching products for provider $providerName: ${e.message}")
            emptyList()
        }
    }

    /**
     * Lấy danh sách đơn hàng liên quan đến nhà cung cấp.
     */
    suspend fun getOrdersForProvider(providerName: String): List<Orders> {
        return try {
            val products = getProductsByProvider(providerName)
            val productIds: List<Long> = products.mapNotNull { product ->
                product.productID.toLong()
            }

            Log.d("ProviderDatabaseHelper", "Product IDs for provider $providerName: $productIds")

            if (productIds.isEmpty()) {
                Log.d("ProviderDatabaseHelper", "No products found for provider: $providerName")
                emptyList()
            } else {
                val ordersSnapshot = firestore.collection("orders")
                    .whereIn("productID", productIds)
                    .get()
                    .await()

                val ordersList = ordersSnapshot.documents.mapNotNull { document ->
                    document.toObject(Orders::class.java)?.apply {
                        orderID = document.id
                    }
                }

                Log.d("ProviderDatabaseHelper", "Orders for provider $providerName: $ordersList")
                ordersList
            }
        } catch (e: Exception) {
            Log.e("ProviderDatabaseHelper", "Error fetching orders for provider $providerName: ${e.message}")
            emptyList()
        }
    }

    /**
     * Log thông tin chi tiết của nhà cung cấp.
     */
    private fun logProviderInfo(providerDoc: DocumentSnapshot) {
        val providerId = providerDoc.getString("providerId") ?: "N/A"
        val providerName = providerDoc.getString("provider") ?: "N/A"
        val email = providerDoc.getString("email") ?: "N/A"
        val phoneNumber = providerDoc.getString("phoneNumber") ?: "N/A"
        val address = providerDoc.getString("address") ?: "N/A"
        val businessCategory = providerDoc.getString("businessCategory") ?: "N/A"
        val logoUrl = providerDoc.getString("logoUrl") ?: "N/A"

        Log.d("ProviderDatabaseHelper", "Provider Info:")
        Log.d("ProviderDatabaseHelper", "ID: $providerId")
        Log.d("ProviderDatabaseHelper", "Name: $providerName")
        Log.d("ProviderDatabaseHelper", "Email: $email")
        Log.d("ProviderDatabaseHelper", "Phone: $phoneNumber")
        Log.d("ProviderDatabaseHelper", "Address: $address")
        Log.d("ProviderDatabaseHelper", "Business Category: $businessCategory")
        Log.d("ProviderDatabaseHelper", "Logo URL: $logoUrl")
    }
}