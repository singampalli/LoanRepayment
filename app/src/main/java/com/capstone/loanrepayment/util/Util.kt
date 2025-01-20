package com.capstone.loanrepayment.util

import android.content.Context
import android.content.SharedPreferences

object TokenManager {
    private const val PREFS_NAME = "app_prefs"
    private const val KEY_AUTH_TOKEN = "auth_token"

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun saveToken(context: Context, token: String) {
        val editor = getSharedPreferences(context).edit()
        editor.putString(KEY_AUTH_TOKEN, token)
        editor.apply()
    }

    fun getToken(context: Context): String? {
        return getSharedPreferences(context).getString(KEY_AUTH_TOKEN, null)
    }

    fun clearToken(context: Context) {
        val editor = getSharedPreferences(context).edit()
        editor.remove(KEY_AUTH_TOKEN)
        editor.apply()
    }
}
