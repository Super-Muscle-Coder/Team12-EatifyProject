package com.example.appbanhangonline.models

//Bảng đánh giá sản phẩm
data class Reviews(
    val reviewID: Int = 0, //Khóa chính
    val userID: Int = 0, //Khóa ngoại tham chiếu đến bảng Users
    val productID: Int = 0, //Khóa ngoại tham chiếu đến bảng Products
    val rating: Float = 0f, //Đánh giá sản phẩm (ví dụ: 4.5)
    val comment: String = "" //Bình luận (nếu có)
)
