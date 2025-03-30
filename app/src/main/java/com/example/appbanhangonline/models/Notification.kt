package com.example.appbanhangonline.models

data class Notification(
    val notificationID: Int = 0, // Khóa chính
    val userID: Int = 0, // Khóa ngoại liên kết với bảng User
    val message: String = "", //Nội dung thông báo
    val notificationType: String = "", //Loại thông báo (ví dụ: Đặt hàng thành công, Đặt hàng thất bại)
    val notificationDate: Long = 0L // Timestamp
)
