package com.example.appbanhangonline.database

import android.content.Context
import android.content.SharedPreferences

object PreferencesHelper {
    private const val PREFS_NAME = "app_prefs"
    private const val KEY_FIRST_LAUNCH = "first_launch"
    private const val KEY_REMEMBER_ME = "remember_me"

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    /* Kiểm tra xem ứng dụng đã được mở lần đầu hay chưa
    fun isFirstLaunch(context: Context): Boolean {
        return false
    }*/

    fun isFirstLaunch(context: Context): Boolean {
    return getPreferences(context).getBoolean(KEY_FIRST_LAUNCH, true)
}
     //Bảo trì xong thì tắt

    // Đặt giá trị cho lần mở đầu tiên
    fun setFirstLaunch(context: Context, isFirst: Boolean) {
        getPreferences(context).edit().putBoolean(KEY_FIRST_LAUNCH, isFirst).apply()
    }

    // Kiểm tra trạng thái "Remember me"
    fun isRememberMe(context: Context): Boolean {
        return getPreferences(context).getBoolean(KEY_REMEMBER_ME, false)
    }

    // Đặt trạng thái "Remember me"
    fun setRememberMe(context: Context, rememberMe: Boolean) {
        getPreferences(context).edit().putBoolean(KEY_REMEMBER_ME, rememberMe).apply()
    }
}
