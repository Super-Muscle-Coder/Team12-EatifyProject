package com.example.appbanhangonline.models

// Bảng Products
data class Products(
    val productID: Int = 0,  // Khóa chính
    val name: String = "",  // Tên sản phẩm
    val nameLowercase: String = "",  // Tên sản phẩm đã được chuyển sang lowercase
    val description: String = "",  // Mô tả sản phẩm (Nếu có)
    val price: Double = 0.0, // Đơn giá sản phẩm
    val provider: String = "",  // Nhà cung cấp ( Dùng làm khóa ngoại )
    val posterURL: String = "", // URL hình ảnh sản phẩm
    val rating: Float = 0f, // Đánh giá sản phẩm (Nếu có)
    val categoryID: Int = 0, // Khóa ngoại tham chiếu đến bảng Categories ( Có cần thiết không ? )
    val productTag: List<String> = listOf(), // Thể loại sản phẩm (ví dụ như đồ ăn ,đồ uống ,...)

    val category: String = "" // Thuộc tính category để lọc và hiển thị sản phẩm theo danh mục
)
