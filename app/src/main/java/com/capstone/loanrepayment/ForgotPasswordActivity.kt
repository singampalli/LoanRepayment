package com.capstone.loanrepayment
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.capstone.loanrepayment.databinding.ActivityForgotPasswordBinding

class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.resetPasswordButton.setOnClickListener {
            // Handle password reset
            val email = binding.emailEditText.text.toString()
            // Perform password reset logic here
        }
    }
}

