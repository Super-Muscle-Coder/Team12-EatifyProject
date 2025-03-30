package com.example.appbanhangonline.viewmodels.loginandregisterViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.example.appbanhangonline.models.Users
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun registerUser(name: String, email: String, password: String, phoneNumber: String, address: String, avatarUrl: String, onComplete: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val userId = auth.currentUser?.uid ?: ""
                        val user = Users(userId, name, email, password, phoneNumber, address, avatarUrl)
                        db.collection("users")
                            .document(user.userId)
                            .set(user)
                            .addOnCompleteListener { firestoreTask ->
                                if (firestoreTask.isSuccessful) {
                                    createCartForUser(userId)
                                    onComplete(true, null)
                                } else {
                                    onComplete(false, firestoreTask.exception?.message)
                                }
                            }
                    } else {
                        onComplete(false, task.exception?.message)
                    }
                }
        }
    }

    private fun createCartForUser(uid: String) {
        val cartDocRef = db.collection("carts").document(uid)
        cartDocRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                if (!task.result.exists()) {
                    val cartData = mapOf(
                        "cartID" to uid,
                        "userID" to uid,
                        "items" to listOf<Map<String, Any>>()
                    )
                    cartDocRef.set(cartData)
                        .addOnSuccessListener {
                            // Cart created successfully
                        }
                        .addOnFailureListener { e ->
                            // Handle error
                        }
                }
            }
        }
    }
}
