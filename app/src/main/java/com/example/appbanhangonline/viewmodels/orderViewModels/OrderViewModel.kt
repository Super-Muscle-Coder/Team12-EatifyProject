package com.example.appbanhangonline.viewmodels.orderViewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appbanhangonline.models.Orders
import com.example.appbanhangonline.models.Products
import com.google.firebase.firestore.FirebaseFirestore

class OrderViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()

    // LiveData để lưu danh sách đơn hàng
    private val _orders = MutableLiveData<List<Orders>>()
    val orders: LiveData<List<Orders>> get() = _orders

    // LiveData để lưu trạng thái đang tải
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    // LiveData để lưu thông báo lỗi (nếu có)
    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    // LiveData để báo thành công đặt hàng (chỉ cho Cash on Delivery)
    private val _orderSuccess = MutableLiveData<Boolean>()
    val orderSuccess: LiveData<Boolean> get() = _orderSuccess

    /****************************************************************************
     * Hàm đặt hàng: khi người dùng chọn phương thức "Cash on Delivery",
     * hàm này sẽ tự sinh một document trong collection "orders" trên Firestore.
     * Sau khi đơn hàng được đặt thành công, nếu phương thức thanh toán là "Cash on Delivery",
     * _orderSuccess được cập nhật là true để UI có thể hiển thị dialog "đặt hàng thành công".
     ****************************************************************************/
    fun placeOrder(
        userID: String,
        product: Products,
        quantity: Int,
        address: String,
        phone: String,
        notes: String,
        paymentMethod: String
    ) {
        val order = hashMapOf(
            "userID" to userID,
            "productID" to product.productID,
            "productName" to product.name,
            "quantity" to quantity,
            "totalPrice" to product.price * quantity,
            "address" to address,
            "phone" to phone,
            "notes" to notes,
            "paymentMethod" to paymentMethod,
            "status" to "Pending",
            "timestamp" to System.currentTimeMillis()
        )

        db.collection("orders")
            .add(order)
            .addOnSuccessListener {
                // Nếu phương thức thanh toán là "Cash on Delivery" thì báo thành công
                if (paymentMethod == "Cash on Delivery") {
                    _orderSuccess.value = true
                }
                println("Order placed successfully")
            }
            .addOnFailureListener { e ->
                println("Error placing order: $e")
                _error.value = e.message
            }
    }

    // Hàm lấy danh sách đơn hàng của người dùng
    fun getUserOrders(userID: String) {
        _loading.value = true
        _error.value = null

        db.collection("orders")
            .whereEqualTo("userID", userID) // Lọc đơn hàng của người dùng
            .get()
            .addOnSuccessListener { querySnapshot ->
                // Log số lượng tài liệu trả về từ Firestore
                Log.d("OrderViewModel", "Number of documents fetched: ${querySnapshot.size()}")

                val orderList = querySnapshot.documents.mapNotNull { document ->
                    // Log dữ liệu thô từng tài liệu
                    Log.d("OrderViewModel", "Document Data: ${document.data}")

                    val order = document.toObject(Orders::class.java)
                    if (order != null) {
                        order.orderID = document.id
                        order.orderType = document.getString("orderType") ?: ""
                        order.items = document.get("items") as? List<Map<String, Any>> ?: emptyList()

                        // Log từng đơn hàng sau khi ánh xạ
                        Log.d("OrderViewModel", "Mapped Order: $order")
                        order
                    } else {
                        null
                    }
                }

                // Log danh sách đơn hàng sau khi ánh xạ
                Log.d("OrderViewModel", "Mapped Orders: $orderList")

                _orders.value = orderList
                _loading.value = false
            }
            .addOnFailureListener { e ->
                // Log lỗi khi truy vấn thất bại
                Log.e("OrderViewModel", "Error fetching orders: ${e.message}")
                _error.value = e.message
                _orders.value = emptyList()
                _loading.value = false
            }
    }
}