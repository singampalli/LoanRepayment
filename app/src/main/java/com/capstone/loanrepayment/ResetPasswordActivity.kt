package com.capstone.loanrepayment

import android.content.Intent
import android.media.session.MediaSession.Token
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.capstone.loanrepayment.services.AuthService
import com.capstone.loanrepayment.util.TokenManager
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class ResetPasswordActivity : AppCompatActivity() {

    private lateinit var passwordEditText: TextInputEditText
    private lateinit var confirmPasswordEditText: TextInputEditText
    private lateinit var resetPasswordButton: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        passwordEditText = findViewById(R.id.passwordEditText)
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText)
        resetPasswordButton = findViewById(R.id.resetPasswordButton)

        resetPasswordButton.setOnClickListener {
            val password = passwordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()

            if (password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Both fields are required", Toast.LENGTH_SHORT).show()
            } else if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            } else {
                val intent = getIntent();
                val userEmail = intent.getStringExtra("email")
                if(userEmail!=null)
                resetPassword(userEmail, password)
                else{
                    Toast.makeText(this, "Something gone wrong, restart Forgot password processes", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, ForgotPasswordActivity::class.java)
                    startActivity(intent)
                }

            }
        }
    }

    private fun resetPassword(userEmail: String, password: String) {
        val call =
            AuthService.resetPassword(userEmail, password) { success, resetCode, errorMessage ->
                if (success) {
                    // Store the token if SharedPreferences
                    Toast.makeText(this, "password updated successfully", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
    }

}