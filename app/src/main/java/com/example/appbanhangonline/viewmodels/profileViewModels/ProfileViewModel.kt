package com.example.appbanhangonline.viewmodels.profileViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appbanhangonline.database.UsersDatabaseHelper
import com.example.appbanhangonline.models.Users

class ProfileViewModel : ViewModel() {
    private val usersDatabaseHelper = UsersDatabaseHelper()

    private val _user = MutableLiveData<Users?>()
    val user: LiveData<Users?> get() = _user

    fun fetchUserById(userId: String) {
        usersDatabaseHelper.fetchUserById(userId, { user ->
            _user.value = user
        }, { exception ->
            // Xử lý lỗi ở đây
        })
    }

    fun logout() {
        // Logic đăng xuất
    }
    fun updateUser(userId: String, updatedInfo: Map<String, Any>, onComplete: (Boolean) -> Unit) {
        usersDatabaseHelper.updateUser(userId, updatedInfo, {
            // Thành công
            onComplete(true)
        }, { error ->
            // Xử lý lỗi
            onComplete(false)
        })
    }
}
