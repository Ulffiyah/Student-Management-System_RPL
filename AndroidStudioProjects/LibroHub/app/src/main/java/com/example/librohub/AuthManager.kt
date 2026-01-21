package com.example.librohub

import android.content.Context
import android.content.SharedPreferences

object AuthManager {
    private const val PREF_NAME = "AuthPrefs"
    private const val KEY_EMAIL = "user_email"
    private const val KEY_PASSWORD = "user_password"

    fun saveUser(context: Context, email: String, password: String) {
        val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().apply {
            putString(KEY_EMAIL, email)
            putString(KEY_PASSWORD, password)
            apply()
        }
    }

    fun getUserEmail(context: Context): String? {
        val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getString(KEY_EMAIL, null)
    }

    fun getUserPassword(context: Context): String? {
        val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getString(KEY_PASSWORD, null)
    }
}