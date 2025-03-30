package com.example.appbanhangonline.models

// Bảng Users
data class Users(
    val userId: String = "",  //Khóa chính
    val name: String = "",  //Tên người dùng
    val email: String = "",  //Địa chỉ email
    val password: String = "",  //Mật khẩu người dùng
    val phoneNumber: String = "", //SÓ điện thoại người dùng
    val address: String = "", //Địa chỉ nhà
    val avatarUrl: String = "" //Avatar
)
