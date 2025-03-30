package com.example.appbanhangonline.models

data class Wishlist(
    val wishlistID: Int = 0, // Khóa chính
    val userID: Int = 0, // Khóa ngoại liên kết với bảng User
    val productID: Int = 0 // Khóa ngoại liên kết với bảng Product
)
