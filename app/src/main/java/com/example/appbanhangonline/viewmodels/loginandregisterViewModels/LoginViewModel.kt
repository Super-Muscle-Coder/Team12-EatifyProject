package com.example.appbanhangonline.viewmodels.loginandregisterViewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appbanhangonline.models.Users
import com.example.appbanhangonline.database.UsersDatabaseHelper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LoginViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val usersDatabaseHelper = UsersDatabaseHelper()

    fun loginUser(email: String, password: String, onComplete: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email, password).await()
                checkAndCreateUser(email)
                onComplete(true, null)
            } catch (e: Exception) {
                onComplete(false, e.message)
            }
        }
    }

    private fun checkAndCreateUser(email: String) {
        // Lấy UID sau khi đăng nhập thành công
        val uid = auth.currentUser?.uid ?: ""
        val userRef = db.collection("users").document(uid)

        // Kiểm tra Document có tồn tại không
        userRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document = task.result
                if (!document.exists()) {
                    // Tạo Document cho người dùng
                    val user = Users(
                        userId = uid,
                        email = email,
                        name = "",
                        password = "",
                        phoneNumber = "",
                        address = "",
                        avatarUrl = ""
                    )
                    userRef.set(user)
                        .addOnSuccessListener {
                            Log.d("LoginViewModel", "User document created successfully.")
                            // Sau khi tạo Document người dùng, tạo Document giỏ hàng dành cho người dùng
                            createCartForUser(uid)
                        }
                        .addOnFailureListener { e ->
                            Log.w("LoginViewModel", "Error creating user document", e)
                        }
                } else {
                    Log.d("LoginViewModel", "User document already exists.")
                    // Kiểm tra xem Document giỏ hàng của người dùng có tồn tại không, nếu không tạo mới
                    createCartForUser(uid)
                }
            } else {
                Log.w("LoginViewModel", "Error fetching user document", task.exception)
            }
        }
    }

    private fun createCartForUser(uid: String) {
        // Ở đây, ta sử dụng uid làm cartID để đơn giản hóa
        val cartDocRef = db.collection("carts").document(uid)
        cartDocRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                if (!task.result.exists()) {
                    // Nếu giỏ hàng chưa tồn tại, tạo giỏ hàng mới
                    val cartData = mapOf(
                        "cartID" to uid,  // Dùng UID làm cartID
                        "userID" to uid,
                        "items" to listOf<Map<String, Any>>()  // Chưa có sản phẩm nào
                    )
                    cartDocRef.set(cartData)
                        .addOnSuccessListener {
                            Log.d("LoginViewModel", "Cart document created successfully.")
                        }
                        .addOnFailureListener { e ->
                            Log.w("LoginViewModel", "Error creating cart document", e)
                        }
                }
            } else {
                Log.w("LoginViewModel", "Error checking cart document", task.exception)
            }
        }
    }
}
