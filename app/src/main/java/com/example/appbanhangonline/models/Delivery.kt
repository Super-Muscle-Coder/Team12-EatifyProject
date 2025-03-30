package com.example.appbanhangonline.models

//Bảng giao hàng
data class Delivery(
    val deliveryID: Int = 0, // Khóa chính
    val orderID: Int = 0, // Khóa ngoại liên kết với bảng Order
    val deliveryStatus: String = "", //Trạng thái của đơn hàng (ví dụ: Chưa giao, Đã giao)
    val deliveryDate: Long = 0L, // Timestamp
    val courierName: String = "",  //Tên vận chuyển
    val trackingNumber: String = ""  //Số theo dõi vận chuyển
)
