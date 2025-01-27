package com.capstone.loanrepayment.util

import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import com.capstone.loanrepayment.MainActivity

object CommonFunctionUtil {
    fun isValidUsername(username: String): Boolean {
        val usernamePattern = "^[a-zA-Z0-9_]{3,20}$"
        return username.isNotEmpty() &&
                username.length >= 3 &&
                username.length <= 20 &&
                username.matches(usernamePattern.toRegex())
    }

    fun isValidEmail(email: String): Boolean {
        val emailPattern = "^[a-zA-Z0-9_]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+\$"
        return email.isNotEmpty() &&
                email.matches(emailPattern.toRegex())
    }

    fun isValidPassword(password: String): Boolean {
//        val passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}$"
        return password.isNotEmpty() &&
                password.length >= 8
//                &&                 password.matches(passwordPattern.toRegex())
    }
}