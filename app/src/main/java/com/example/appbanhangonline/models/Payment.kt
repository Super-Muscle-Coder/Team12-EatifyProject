package com.example.appbanhangonline.models

//Bảng thanh toán (Payment)
data class Payment(
    val paymentID: Int = 0, // Khóa chính
    val orderID: Int = 0, // Khóa ngoại liên kết với bảng Order
    val userID: Int = 0, // Khóa ngoại liên kết với bảng User
    val paymentMethod: String = "", //Phương thức thanh toán (ví dụ: PayPal, Credit Card)
    val paymentStatus: String = "", //Trạng thái thanh toán (ví dụ: Chưa thanh toán, Đã thanh toán)
    val paymentDate: Long = 0L // Timestamp
)





