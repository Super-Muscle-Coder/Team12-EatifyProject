package com.example.appbanhangonline.models

data class Orders(
    var orderID: String = "",
    var orderType: String = "",
    var userID: String = "",
    var productName: String = "",
    var quantity: Int = 0,
    var totalPrice: Double = 0.0,
    var address: String = "",
    var phone: String = "",
    var notes: String = "",
    var paymentMethod: String = "",
    var status: String = "Pending",
    var timestamp: Long = 0L,
    var items: List<Map<String, Any>> = emptyList() // Add this
)
