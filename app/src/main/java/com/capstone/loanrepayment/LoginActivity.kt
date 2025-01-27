package com.capstone.loanrepayment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.capstone.loanrepayment.databinding.ActivityLoginBinding
import com.capstone.loanrepayment.services.AuthService
import com.capstone.loanrepayment.util.CommonFunctionUtil
import com.capstone.loanrepayment.util.ToastUtil
import com.capstone.loanrepayment.util.TokenManager

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener {
            // Handle login
            val username = binding.usernameEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            if (validateCredentials(username, password)) {
                AuthService.authenticateUser(username, password) { success, token, errorMessage ->
                    if (success) {
                        // Store the token if SharedPreferences
                        if (token != null) {
                            TokenManager.saveToken(this, token)
                            TokenManager.saveUserName(this, username)
                        }
                        ToastUtil.showSuccessToast(this, getString(R.string.login_successful))
                        // Navigate to MainActivity
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent.putExtra("username", username))
                        finish()
                    } else {
                        Log.e("failed at login", errorMessage.toString())
                        ToastUtil.showErrorToast(this, getString(R.string.login_error_message))
                    }
                }
            }

        }
        binding.forgotPasswordTextView.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }
        binding.signUpTextView.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }
    }

    fun validateCredentials(username: String, password: String): Boolean {
        // Retrieve username and password from EditText

        // Validate username and password
        val isUsernameValid = CommonFunctionUtil.isValidUsername(username)
        val isPasswordValid = CommonFunctionUtil.isValidPassword(password)
        val errorMessages = mutableListOf<String>()
        if (!isUsernameValid) {
            errorMessages.add(getString(R.string.invalid_username))
        }
        if (!isPasswordValid) {
            errorMessages.add(getString(R.string.invalid_password))
        }
        if (errorMessages.isNotEmpty()) {
            val combinedErrors = errorMessages.joinToString(separator = "\n")
            ToastUtil.showErrorToast(this, combinedErrors)
            return false;
        }
        return true;
    }
}


