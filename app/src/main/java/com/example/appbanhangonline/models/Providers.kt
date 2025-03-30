package com.example.appbanhangonline.models

// Bảng Providers
data class Providers(
    val providerId: String = "",  // Khóa chính - ID nhà cung cấp
    val provider: String = "",  // Tên nhà cung cấp (sửa từ "name" thành "provider")
    val email: String = "",  // Địa chỉ email
    val password: String = "",  // Mật khẩu (nên mã hóa trước khi lưu)
    val phoneNumber: String = "",  // Số điện thoại liên hệ
    val address: String = "",  // Địa chỉ doanh nghiệp
    val businessCategory: String = "",  // Danh mục kinh doanh (ví dụ: "Đồ ăn", "Đồ uống")
    val logoUrl: String = "",  // URL ảnh logo (nếu có)
    val createdAt: Long = System.currentTimeMillis(),  // Thời gian tạo tài khoản
    val updatedAt: Long = System.currentTimeMillis()  // Thời gian cập nhật thông tin cuối cùng
)
