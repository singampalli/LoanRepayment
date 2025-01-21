package com.capstone.loanrepayment
import android.content.Intent
import android.media.session.MediaSession.Token
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
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
        codeEditText = findViewById(R.id.codeEditText);

        resetPasswordButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val resetCodeInput = codeEditText.text.toString();

            if (!resetCodeInput.isEmpty() && !emailEditText.isVisible) {
                if(resetCodeInput == TokenManager.getResetCode(this)){
                    Toast.makeText(this,"Reset to new password",Toast.LENGTH_LONG).show()
                    val intent=Intent(this, ResetPasswordActivity::class.java)
                    startActivity(intent.putExtra("email",email))
                    finish()
                }
                else
                    Toast.makeText(this,"Code is invalied",Toast.LENGTH_SHORT).show()

            } else {
                val call = AuthService.forgotPassword(email){success, resetCode, errorMessage ->
                    if (success) {
                        // Store the token if SharedPreferences
                        if (resetCode != null) {
                            TokenManager.saveResetCode(this, resetCode)
                            codeInput = findViewById(R.id.codeInput)
                            codeInput.visibility = View.VISIBLE
                            emailEditText.visibility = View.GONE
                            Toast.makeText(this, "Code sent to your email successfully", Toast.LENGTH_SHORT).show()
                        };
                    } else {
                        Toast.makeText(this, "reset password call failed", Toast.LENGTH_SHORT).show()
                    }

                }
            }
        }
    }
}
