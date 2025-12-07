package com.example.todolist.utils

import android.content.Context
import android.content.SharedPreferences

class MyPreferenceManager(context: Context) {

    companion object {
        private const val PREF_NAME = "MyPrefs"
        private const val KEY_USERNAME = "username"
        private const val KEY_EMAIL = "email"
    }

    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    // -----------------------------
    // Username
    // -----------------------------
    fun setUsername(name: String) {
        prefs.edit().putString(KEY_USERNAME, name).apply()
    }

    fun getUsername(): String {
        return prefs.getString(KEY_USERNAME, "User") ?: "User"
    }

    // -----------------------------
    // Email
    // -----------------------------
    fun setEmail(email: String) {
        prefs.edit().putString(KEY_EMAIL, email).apply()
    }

    fun getEmail(): String {
        return prefs.getString(KEY_EMAIL, "") ?: ""
    }

    // -----------------------------
    // Generic String
    // -----------------------------
    fun putString(key: String, value: String) {
        prefs.edit().putString(key, value).apply()
    }

    fun getString(key: String, defaultValue: String? = null): String? {
        return prefs.getString(key, defaultValue)
    }

    // -----------------------------
    // Boolean
    // -----------------------------
    fun putBoolean(key: String, value: Boolean) {
        prefs.edit().putBoolean(key, value).apply()
    }

    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean {
        return prefs.getBoolean(key, defaultValue)
    }

    // -----------------------------
    // Clear All
    // -----------------------------
    fun clear() {
        prefs.edit().clear().apply()
    }
}
