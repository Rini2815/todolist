package com.example.todolist.utils

import android.content.Context

class PreferenceManager(context: Context) {

    private val pref = context.getSharedPreferences("USER_PREF", Context.MODE_PRIVATE)

    fun setName(name: String) {
        pref.edit().putString("NAME", name).apply()
    }

    fun getName(): String? {
        return pref.getString("NAME", "")
    }

    fun setEmail(email: String) {
        pref.edit().putString("EMAIL", email).apply()
    }

    fun getEmail(): String? {
        return pref.getString("EMAIL", "")
    }

    fun setPassword(password: String) {
        pref.edit().putString("PASSWORD", password).apply()
    }

    fun getPassword(): String? {
        return pref.getString("PASSWORD", "")
    }

    fun setLoggedIn(status: Boolean) {
        pref.edit().putBoolean("LOGGED_IN", status).apply()
    }

    fun isLoggedIn(): Boolean {
        return pref.getBoolean("LOGGED_IN", false)
    }

    fun clear() {
        pref.edit().clear().apply()
    }
}
