package com.example.todolist.utils

import android.content.Context
import android.net.Uri

class PreferenceManager(context: Context) {

    private val pref = context.getSharedPreferences("todolist_pref", Context.MODE_PRIVATE)

    fun setLogin(status: Boolean) {
        pref.edit().putBoolean("isLogin", status).apply()
    }

    fun isLogin(): Boolean {
        return pref.getBoolean("isLogin", false)
    }

    fun setUsername(name: String) {
        pref.edit().putString("username", name).apply()
    }

    fun getUsername(): String {
        return pref.getString("username", "User") ?: "User"
    }

    // Foto profil (URI string)
    fun setProfileImage(uri: Uri?) {
        pref.edit().putString("profile_image", uri?.toString()).apply()
    }

    fun getProfileImage(): Uri? {
        val str = pref.getString("profile_image", null)
        return if (str != null) Uri.parse(str) else null
    }

    // Theme (Light/Dark)
    fun setTheme(mode: String) {
        pref.edit().putString("theme_mode", mode).apply()
    }

    fun getTheme(): String {
        return pref.getString("theme_mode", "light") ?: "light"
    }
}