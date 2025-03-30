package com.example.appbanhangonline.database

import com.example.appbanhangonline.models.Users
import com.google.firebase.firestore.FirebaseFirestore
import android.util.Log
import java.util.Locale

class UsersDatabaseHelper {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val adminID = "admin_id_12345" // ID của admin

    private fun checkAdmin(userID: String): Boolean {
        return userID == adminID
    }

    // Thêm người dùng mới ( Quyền của admin )
    fun addUser(user: Users, currentUserID: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        if (!checkAdmin(currentUserID)) {
            onFailure(Exception("Access denied. Only admin can add users."))
            return
        }

        db.collection("users")
            .add(user)
            .addOnSuccessListener {
                Log.d("UserDatabaseHelper", "User added successfully")
                onSuccess()
            }
            .addOnFailureListener { e ->
                Log.w("UserDatabaseHelper", "Error adding user", e)
                onFailure(e)
            }
    }

    // Lấy danh sách người dùng
    fun fetchUsers(onSuccess: (List<Users>) -> Unit, onFailure: (Exception) -> Unit) {
        db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                val users = result.map { it.toObject(Users::class.java) }
                onSuccess(users)
            }
            .addOnFailureListener { e ->
                Log.w("UserDatabaseHelper", "Error fetching users", e)
                onFailure(e)
            }
    }

    // Cập nhật thông tin người dùng ( Quyền của admin )
    fun updateUser(userId: String, updatedUser: Map<String, Any>, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        db.collection("users").document(userId)
            .update(updatedUser)
            .addOnSuccessListener {
                Log.d("UserDatabaseHelper", "User updated successfully")
                onSuccess()
            }
            .addOnFailureListener { e ->
                Log.w("UserDatabaseHelper", "Error updating user", e)
                onFailure(e)
            }
    }

    // Xóa người dùng (chỉ dành cho quản trị viên)
    fun deleteUser(userId: String, currentUserID: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        if (!checkAdmin(currentUserID)) {
            onFailure(Exception("Access denied. Only admin can delete users."))
            return
        }

        db.collection("users").document(userId)
            .delete()
            .addOnSuccessListener {
                Log.d("UserDatabaseHelper", "User deleted successfully")
                onSuccess()
            }
            .addOnFailureListener { e ->
                Log.w("UserDatabaseHelper", "Error deleting user", e)
                onFailure(e)
            }
    }

    //Hàm fetchUserById sẽ lấy thông tin người dùng từ firebase thông qua UID của hộ
    fun fetchUserById(userId: String, onSuccess: (Users?) -> Unit, onFailure: (Exception) -> Unit) {
        db.collection("users").document(userId)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val user = document.toObject(Users::class.java)
                    Log.d("UserDatabaseHelper", "User fetched successfully: $user")
                    onSuccess(user)
                } else {
                    Log.w("UserDatabaseHelper", "User not found")
                    onFailure(Exception("User not found"))
                }
            }
            .addOnFailureListener { e ->
                Log.w("UserDatabaseHelper", "Error fetching user", e)
                onFailure(e)
            }
    }

    // Hàm fetchUserByEmail: lấy thông tin người dùng theo email (email được dùng làm document id)
    fun fetchUserByEmail(
        email: String,
        onSuccess: (Users?) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        // email được chuyển sang chữ thường trước khi truy vấn
        val userRef = db.collection("users").document(email.toLowerCase(Locale.ROOT))
        userRef.get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val user = document.toObject(Users::class.java)
                    Log.d("UserDatabaseHelper", "User fetched successfully: $user")
                    onSuccess(user)
                } else {
                    Log.w("UserDatabaseHelper", "User not found")
                    onFailure(Exception("User not found"))
                }
            }
            .addOnFailureListener { e ->
                Log.w("UserDatabaseHelper", "Error fetching user", e)
                onFailure(e)
            }
    }

}


