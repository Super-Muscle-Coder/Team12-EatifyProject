package com.example.appbanhangonline.models

import java.util.Date

data class Carts(
    val cartID: String, // ID Giỏ Hàng cố định cho mỗi người dùng ( lấy chính UID để tự sinh )
    val userID: String, // ID Người Dùng (Khóa ngoại tham chiếu đến bảng Users)
    val productID: Int, // ID Sản Phẩm (Khóa ngoại tham chiếu đến bảng Products)
    val quantity: Int, // Số Lượng Sản Phẩm
    val unitPrice: Double, // Đơn Giá của mỗi sản phẩm
    val totalPrice: Double = quantity * unitPrice, // Tổng giá trị của sản phẩm trong giỏ hàng
    val specialRequest: String = "", // Yêu cầu đặc biệt (nếu có)
    val timestamp: Date = Date(System.currentTimeMillis()), // Thời gian thêm sản phẩm vào giỏ hàng
    val name : String = ""
)
