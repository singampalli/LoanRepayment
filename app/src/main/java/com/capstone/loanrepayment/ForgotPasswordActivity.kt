package com.capstone.loanrepayment
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.capstone.loanrepayment.databinding.ActivityForgotPasswordBinding
import com.capstone.loanrepayment.services.AuthService
import com.capstone.loanrepayment.util.TokenManager
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

//class ForgotPasswordActivity : AppCompatActivity() {
//    private lateinit var binding: ActivityForgotPasswordBinding
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        binding.resetPasswordButton.setOnClickListener {
//            // Handle password reset
//            val email = binding.emailEditText.text.toString()
//            // Perform password reset logic here
//        }
//    }
//}

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var emailEditText: TextInputEditText
    private lateinit var emailInput: TextInputLayout
    private lateinit var resetPasswordButton: MaterialButton
    private lateinit var codeInput: TextInputLayout
    private lateinit var codeEditText: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        emailInput= findViewById(R.id.emailInput)
        emailEditText = findViewById(R.id.emailEditText)
        resetPasswordButton = findViewById(R.id.resetPasswordButton)

        resetPasswordButton.setOnClickListener {
            val email = emailEditText.text.toString()

            if (email.isEmpty()) {
                val resetCodeInput = codeEditText.text.toString()
                emailEditText.error = "Email is required"
            } else {
            //    AuthService.authenticateUser(username, password) { success, token, errorMessage ->

                val call = AuthService.resetPassword(email){success, resetCode, errorMessage ->
                    if (success) {
                        // Store the token if SharedPreferences
                        if (resetCode != null) {
                            TokenManager.saveResetCode(this, resetCode)
                            Toast.makeText(this, "every thing is fine1", Toast.LENGTH_SHORT).show()
                            codeInput = findViewById(R.id.codeInput)
                            codeInput.visibility = View.VISIBLE
                            emailEditText.setText("")
                            emailEditText.visibility = View.GONE
                            Toast.makeText(this, "every thing is fine2", Toast.LENGTH_SHORT).show()
                        };

                    } else {
                        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                    }

                }
            }
        }
    }
}
