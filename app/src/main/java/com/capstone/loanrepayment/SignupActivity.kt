package com.capstone.loanrepayment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.capstone.loanrepayment.services.AuthService
import com.capstone.loanrepayment.util.ToastUtil


class SignupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val etUsername: EditText = findViewById(R.id.etUsername)
        val etEmail: EditText = findViewById(R.id.etEmail)
        val etPassword: EditText = findViewById(R.id.etPassword)
        val btnSignUp: Button = findViewById(R.id.btnSignUp)

        btnSignUp.setOnClickListener {
            val username = etUsername.text.toString()
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                ToastUtil.showErrorToast(this, getString(R.string.all_fields_needed))
            } else {
                // Handle sign-up logic here
                AuthService.registerUser(
                    username,
                    email,
                    password
                ) { success, token, errorMessage ->
                    if (success) {
                        // Navigate to MainActivity
                        ToastUtil.showSuccessToast(this, getString(R.string.signup_success))
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent);
                    } else {
                        Log.e("SignupActivity", errorMessage!!)
                        ToastUtil.showErrorToast(this, errorMessage)
                    }
                }
            }
        }
    }
}