package com.capstone.loanrepayment


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.capstone.loanrepayment.databinding.ActivityLoginBinding
import com.capstone.loanrepayment.services.AuthService

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

            AuthService.authenticateUser(username, password) { success, token, errorMessage ->
                if (success) {
                    // Store the token if necessary
                    val sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString("auth_token", token)
                    editor.apply()

                    // Navigate to MainActivity
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }


        binding.forgotPasswordTextView.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }
    }
}
