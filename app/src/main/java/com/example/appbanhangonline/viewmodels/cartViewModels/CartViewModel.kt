package com.example.appbanhangonline.viewmodels.cartViewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appbanhangonline.models.Carts
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Date

class CartViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    val cartItems = MutableLiveData<MutableList<Carts>>(mutableListOf())

    /**
     * Thêm sản phẩm vào giỏ hàng.
     */
    fun addToCart(userID: String, productID: Int, name: String, quantity: Int, unitPrice: Double, specialRequest: String) {
        val cartID = userID
        val currentCartItems = cartItems.value ?: mutableListOf()
        val existingCartItemIndex = currentCartItems.indexOfFirst { it.cartID == cartID && it.productID == productID && it.userID == userID }

        if (existingCartItemIndex != -1) {
            val existingCartItem = currentCartItems[existingCartItemIndex]
            val updatedCartItem = existingCartItem.copy(
                quantity = existingCartItem.quantity + quantity,
                totalPrice = (existingCartItem.quantity + quantity) * existingCartItem.unitPrice
            )
            currentCartItems[existingCartItemIndex] = updatedCartItem
        } else {
            val newCartItem = Carts(
                cartID = cartID,
                userID = userID,
                productID = productID,
                name = name,
                quantity = quantity,
                specialRequest = specialRequest,
                unitPrice = unitPrice,
                totalPrice = quantity * unitPrice,
                timestamp = Date(System.currentTimeMillis())
            )
            currentCartItems.add(newCartItem)
        }

        // Log thêm sản phẩm vào giỏ hàng
        Log.d("CartViewModel", "Added productID: $productID with quantity: $quantity. Current cart: $currentCartItems")

        // Thông báo thay đổi danh sách giỏ hàng
        cartItems.value = currentCartItems.toMutableList()
    }

    /**
     * Cập nhật số lượng sản phẩm trong giỏ hàng.
     */
    /**
     * Cập nhật số lượng sản phẩm trong giỏ hàng.
     */
    fun updateQuantity(userID: String, productID: Int, quantity: Int) {
        val currentCartItems = cartItems.value?.toMutableList() ?: mutableListOf()
        val existingCartItemIndex = currentCartItems.indexOfFirst { it.userID == userID && it.productID == productID }

        if (existingCartItemIndex != -1) {
            val existingCartItem = currentCartItems[existingCartItemIndex]
            val updatedCartItem = existingCartItem.copy(
                quantity = quantity,
                totalPrice = quantity * existingCartItem.unitPrice
            )
            currentCartItems[existingCartItemIndex] = updatedCartItem

            // Log sau khi cập nhật
            Log.d("CartViewModel", "Updated quantity for productID: $productID to $quantity. Total price: ${updatedCartItem.totalPrice}")

            // Tạo danh sách mới để kích hoạt LiveData
            cartItems.value = currentCartItems
        }
    }


    /**
     * Xóa sản phẩm khỏi giỏ hàng.
     */
    fun removeFromCart(userID: String, productID: Int) {
        val currentCartItems = cartItems.value ?: mutableListOf()
        val updatedCartItems = currentCartItems.filterNot { it.userID == userID && it.productID == productID }.toMutableList()

        // Log khi xóa sản phẩm
        Log.d("CartViewModel", "Removed productID: $productID. Updated cart: $updatedCartItems")

        cartItems.value = updatedCartItems
    }


    /**
     * Xóa toàn bộ sản phẩm trong giỏ hàng.
     */
    fun clearCart(userID: String) {
        cartItems.value = mutableListOf()
    }

    fun calculateTotalPrice(userID: String): Double {
        return cartItems.value?.sumOf { it.totalPrice } ?: 0.0
    }

    /**
     * Đặt hàng.
     */
    fun placeOrder(
        userID: String,
        address: String,
        phone: String,
        notes: String,
        orderType: String,
        paymentMethod: String,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) {
        // Kiểm tra và log userID
        if (userID.isBlank()) {
            Log.e("CartViewModel", "Failed to place order: userID is blank")
            onError(Exception("userID cannot be blank"))
            return
        }
        val currentCartItems = cartItems.value ?: mutableListOf()
        val totalPrice = currentCartItems.sumOf { it.totalPrice }

        val orderData = hashMapOf(
            "userID" to userID, // Đảm bảo userID được lưu
            "items" to currentCartItems.map { item ->
                hashMapOf(
                    "productID" to item.productID,
                    "name" to item.name,
                    "quantity" to item.quantity,
                    "unitPrice" to item.unitPrice,
                    "totalPrice" to item.totalPrice
                )
            },
            "totalPrice" to totalPrice,
            "address" to address,
            "phone" to phone,
            "notes" to notes,
            "paymentMethod" to paymentMethod,
            "orderType" to orderType,
            "timestamp" to System.currentTimeMillis()
        )

        // Log dữ liệu trước khi lưu
        Log.d("CartViewModel", "Placing order with data: $orderData")

        db.collection("orders")
            .add(orderData)
            .addOnSuccessListener { documentReference ->
                // Log khi lưu thành công
                Log.d("CartViewModel", "Order placed successfully with ID: ${documentReference.id}")
                clearCart(userID)
                onSuccess()
            }
            .addOnFailureListener { exception ->
                // Log lỗi nếu không lưu sản phẩm thành công
                Log.e("CartViewModel", "Error placing order: ${exception.message}")
                onError(exception)
            }
    }
}