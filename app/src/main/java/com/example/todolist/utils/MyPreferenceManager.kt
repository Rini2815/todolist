package com.example.todolist.utils

import android.content.Context
import android.content.SharedPreferences

class MyPreferenceManager(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

    // -----------------------------
    // Username
    // -----------------------------
    fun setUsername(name: String) {
        prefs.edit().putString("username", name).apply()
    }

    fun getUsername(): String {
        return prefs.getString("username", "User") ?: "User"
    }

    // -----------------------------
    // Simpan string ke SharedPreferences
    // -----------------------------
    fun putString(key: String, value: String) {
        prefs.edit().putString(key, value).apply()
    }

    // Ambil string dari SharedPreferences
    fun getString(key: String, defaultValue: String? = null): String? {
        return prefs.getString(key, defaultValue)
    }

    // -----------------------------
    // Hapus semua data
    // -----------------------------
    fun clear() {
        prefs.edit().clear().apply()
    }
}
