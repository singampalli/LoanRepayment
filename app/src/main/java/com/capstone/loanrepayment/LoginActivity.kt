package com.capstone.loanrepayment


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.capstone.loanrepayment.databinding.ActivityLoginBinding
import com.capstone.loanrepayment.services.AuthService
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

            AuthService.authenticateUser(username, password) { success, token, errorMessage ->
                if (success) {
                    // Store the token if SharedPreferences
                    if (token != null) {
                        TokenManager.saveToken(this, token)
                    };
                    // Navigate to MainActivity
                    val intent=Intent(this, MainActivity::class.java)
                    startActivity(intent.putExtra("username",username))
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
